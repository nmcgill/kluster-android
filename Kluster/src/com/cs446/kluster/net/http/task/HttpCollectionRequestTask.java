package com.cs446.kluster.net.http.task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.JsonReader;

import com.cs446.kluster.data.StorageAdapter;
import com.cs446.kluster.data.serialize.Serializer;
import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.Request;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class HttpCollectionRequestTask<Result> extends AbstractHttpRequestTask<Result> {

    private StorageAdapter<Result> mStorageAdapter = null;

    public HttpCollectionRequestTask(HttpRequestListener<Result> listener, Serializer<Result> serializer, StorageAdapter<Result> storageAdapter) {
        super(listener, serializer);
        this.mStorageAdapter = storageAdapter;
    }

    public HttpCollectionRequestTask(Serializer<Result> serializer, StorageAdapter<Result> storageAdapter) {
        this(null, serializer, storageAdapter);
    }

    public void executeAsync(final Request request) {
        final Handler handler = new Handler();

        if (this.mListener != null) {
            this.mListener.onStart();
        }

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final Result result = doWork(request);

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (HttpCollectionRequestTask.this.mListener != null) {
                            HttpCollectionRequestTask.this.mListener.onComplete();
                            if (HttpCollectionRequestTask.this.mError != null) {
                                HttpCollectionRequestTask.this.mListener.onError(HttpCollectionRequestTask.this.mError);
                                return;
                            }
                            HttpCollectionRequestTask.this.mListener.onSuccess(result);
                        }
                    }
                });
            }
        });
    }

    public Result execute(Request request) {
        return this.doWork(request);
    }

    protected List<Result> parse(HttpEntity content) throws IOException {
    	List<Result> list = new ArrayList<Result>();
    	Reader reader = new InputStreamReader(content.getContent());
        JsonReader jr = new JsonReader(reader);

        if (this.mSerializer != null) {
	        try {
	        	jr.beginArray();
	            while (jr.hasNext()) {
	                list.add(this.mSerializer.read(jr));
	            }
	            jr.endArray();
		    } catch (IOException e) {
		        return null;
		    } finally {
		        jr.close();
		    }
        }
        
        return list;
    }

    protected void store(Result result) {
        if (this.mStorageAdapter != null) {
            this.mStorageAdapter.upsert(result);
        }
    }

    private Result doWork(Request request) {
        /*HttpURLConnection connection = null;
        this.mError = null;

        try {
            connection = createConnection(request);
            connection.connect();

            Response response = Response.create(connection);
            response.ensureSuccess();

            Result result = parse(response);
            if (result != null) {
                store(result);
            }
            return result;
        } catch (Exception e) {
            this.mError = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;*/
        HttpUriRequest req = request.getHttpRequest();
        HttpClient httpclient = new DefaultHttpClient();
        this.mError = null;

        try {
            HttpResponse response = httpclient.execute(req);
            if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
                this.mError = new Exception(response.getStatusLine().toString());
                return null;
            }
            List<Result> result = parse(response.getEntity());
            if (result != null) {
            	for (Result res : result) {
                	store(res);
            	}
            }
            return null;
        } catch (Exception e) {
            this.mError = e;
        }

        return null;
    }
}