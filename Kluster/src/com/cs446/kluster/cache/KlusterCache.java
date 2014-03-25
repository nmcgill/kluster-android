package com.cs446.kluster.cache;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class KlusterCache {
	private static KlusterCache instance = null;
	private static Context mContext;
	private static boolean flinging = false;  

	/* Memory Cache */
	private Bitmap mPlaceHolderBitmap;
	private static LruCache<String, Bitmap> mMemoryCache;
	
	/* Disk Cache */
	private DiskLruImageCache mDiskLruCache;
	private final Object mDiskCacheLock = new Object();
	private boolean mDiskCacheStarting = true;
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 25; // 25MB
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	
	protected KlusterCache(Context c) {
		mContext = c;
		InitCache();
	}
	
	public static KlusterCache getInstance(Context c) {
		if (instance == null) {
			instance = new KlusterCache(c);
		}
		
		return instance;
	}
		
	private void InitCache() {
		
		// Initialize memory cache
        if (mMemoryCache == null) {
    		// Get max available VM memory
    		// Stored in kilobytes as LruCache takes an int in its constructor
    	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    	    // Use 1/4th of the available memory for this memory cache.
    	    final int cacheSize = maxMemory / 4;

    	    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
    	        @Override
    	        protected int sizeOf(String key, Bitmap bitmap) {
    	            // The cache size will be measured in kilobytes rather than number of items
    	            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    	        }
    	    };
        }
                	    	    
		// Initialize disk cache on background thread
		File cacheDir = DiskLruImageCache.getDiskCacheDir(mContext, DISK_CACHE_SUBDIR);
		new InitDiskCacheTask(mContext).execute(cacheDir);
	}
	
	private void addBitmapToCache(String key, Bitmap bitmap) {
	    // Add to memory cache as before
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }

	    // Also add to disk cache
	    synchronized (mDiskCacheLock) {
	        if (mDiskLruCache != null && mDiskLruCache.getBitmap(key) == null) {
	            mDiskLruCache.put(key, bitmap);
	        }
	    }  
	}
	
	private Bitmap getBitmapFromDiskCache(String key) {
	    synchronized (mDiskCacheLock) {
	        // Wait while disk cache is started from background thread
	        while (mDiskCacheStarting) {
	            try {
	                mDiskCacheLock.wait();
	            } catch (InterruptedException e) {}
	        }
	        if (mDiskLruCache != null) {
	            return mDiskLruCache.getBitmap(key);
	        }
	    }
	    return null;
	}

	private Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}
	
	public void loadBitmap(String url, ImageView imageView, Context c) {
		
		if (url.startsWith("http")) {
			loadBitmapfromUrl(url, imageView, c);
		}
		else {
			loadBitmapfromFile(url, imageView, c);
		}
	}
		
	
	public void loadBitmapfromFile(String file, ImageView imageView, Context c) {
		final String imageKey = file;

	    Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	    	imageView.setImageBitmap(bitmap);
	    } else {
	    	
		    if (cancelPotentialWork(file, imageView)) {
		        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		        final AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
		        imageView.setImageDrawable(asyncDrawable);
		        task.execute(file, "file");
		    }
	    }
	}
	
	public void loadBitmapfromUrl(String url, ImageView imageView, Context c) {
		final String imageKey = url.substring(url.lastIndexOf('/')+1);

	    Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	    	imageView.setImageBitmap(bitmap);
	    } else {
	    	
		    if (cancelPotentialWork(url, imageView)) {
		        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		        final AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
		        imageView.setImageDrawable(asyncDrawable);
		        task.execute(url, "url");
		    }
	    }
	}
	
	private static boolean cancelPotentialWork(String data, ImageView imageView) {
	    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

	    if (bitmapWorkerTask != null) {
	        final String bitmapData = bitmapWorkerTask.data;
	        if (bitmapData != data) {
	            // Cancel previous task
	            bitmapWorkerTask.cancel(true);
	        } else {
	            // The same work is already in progress
	            return false;
	        }
	    }
	    // No task associated with the ImageView, or an existing task was cancelled
	    return true;
	}
	
	private static Bitmap decodeSampledBitmapFromResource(String link, String type) {
		
		if (type == "file") {
    		BitmapFactory.Options options = new BitmapFactory.Options();
    		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    		return BitmapFactory.decodeFile(link, options);			
		}
		else {
	        try {
	            URL url = new URL(link);
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	            return BitmapFactory.decodeStream(conn.getInputStream());
	        }
	        catch (Exception e) {
	        	Log.e("Image_download", "Could not download: " + link);
	        }	
		}
        
        return null;
	}
	
	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
	   if (imageView != null) {
	       final Drawable drawable = imageView.getDrawable();
	       if (drawable instanceof AsyncDrawable) {
	           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
	           return asyncDrawable.getBitmapWorkerTask();
	       }
	    }
	    return null;
	}
	
	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    private final WeakReference<ImageView> imageViewReference;
	    private String data = "";
	    
	    public BitmapWorkerTask(ImageView imageView) {
	        // Use a WeakReference to ensure the ImageView can be garbage collected
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    // Download image in background.
	    @Override
	    protected Bitmap doInBackground(String... params) {
	        data = params[0];
	        String type = params[1];
	        final String imageKey = (type == "url" ? data.substring(data.lastIndexOf('/')+1) : data);
	        
	    	//Lock thread while scrolling
	    	while (flinging) {
	    		//If our ImageView disappears while we are locked, return nothing
	    		if (imageViewReference.get() == null)
	    			return null;
	    	}	

	        // Check disk cache in background thread
	        Bitmap bitmap = getBitmapFromDiskCache(imageKey);
	        
	        if (bitmap == null) { // Not found in disk cache
	        	bitmap = decodeSampledBitmapFromResource(data, type);
	        }

	        if (bitmap != null) {
		        // Add final bitmap to caches
		        addBitmapToCache(imageKey, bitmap);
	        }
	        
	        return bitmap;
	    }

	    // Once complete, see if ImageView is still around and set bitmap.
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        if (isCancelled()) {
	            bitmap = null;
	        }

	        if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
	            if (this == bitmapWorkerTask && imageView != null) {            	
	            	Animation animation = new AlphaAnimation(0.0f, 1.0f);
	            	animation.setDuration(250);
	                imageView.setImageBitmap(bitmap);

	                imageView.startAnimation(animation);
	            }
	        }
	    }
	}
	
	private static class AsyncDrawable extends BitmapDrawable {
	    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

	    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
	        super(res, bitmap);
	        bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
	    }

	    public BitmapWorkerTask getBitmapWorkerTask() {
	        return bitmapWorkerTaskReference.get();
	    }
	}
	
	private class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
		private Context mContext;
		
		public InitDiskCacheTask(Context c) {
			mContext = c;
		}
		
	    @Override
	    protected Void doInBackground(File... params) {

			synchronized (mDiskCacheLock) {
	            File cacheDir = params[0];
	            mDiskLruCache = new DiskLruImageCache(mContext, cacheDir, DISK_CACHE_SIZE);
	            mDiskCacheStarting = false; // Finished initialization
	            mDiskCacheLock.notifyAll(); // Wake any waiting threads
	        }
	        return null;
	    }
	}
}
