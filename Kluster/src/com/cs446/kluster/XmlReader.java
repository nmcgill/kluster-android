package com.cs446.kluster;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.util.Xml;


/** TODO: MODIFY THIS TO MATCH KLUSTER JSON */
/**                                         */
/**                                         */
/**                                         */
/**                                         */

public class XmlReader {
	private static final String ns = null;
	private int mUserID;
	private ContentResolver mContentResolver;
	
	public XmlReader(ContentResolver c, int user) {
		mContentResolver = c;
		mUserID = user;
	}
	
    private void AddtoPhotoProvider(Photo item) {

    	//**TODO: DO WE EVEN WANT TO USE A PROVIDER? */
    	
    		ContentValues values = new ContentValues();
    		DateFormat df = new SimpleDateFormat("MMM dd h:mmaa", Locale.US);
    		
    		values.put("title", item.getPhotoId());
    		values.put("location", item.getLocation().toString());

    		
    		//UNCOMMENT THIS LINE IF WE ARE USING A PROVIDER
    		//mContentResolver.insert(PhotoProvider.CONTENT_URI, values);
    }
    
    public Boolean parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private Boolean readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
    	
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("item")) {
            	AddtoPhotoProvider(readPhoto(parser));
            } else {
                skip(parser);
            }
        }
        
        return true;
    }
    
    private Photo readPhoto(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        int id = 0;
        Location loc = null;
        
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("id")) {
                id = Integer.parseInt(readTag(parser, "id"));
            }
            else if (name.equals("loc")) {
                loc = new Location(readTag(parser, "loc"));
            }
            else {
                skip(parser);
            }
        }
        
        return new Photo(id, loc);
    }

    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }
    
    private Date readDate(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
    	try {
	        parser.require(XmlPullParser.START_TAG, ns, tag);
	        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
	        Date dte = df.parse(readText(parser));
	        parser.require(XmlPullParser.END_TAG, ns, tag);
	        
	        return dte;
    	}
    	catch (Exception e) {
    		return null;
    	}
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = getXMLString(parser.getText());
            parser.nextTag();
        }
        return result;
    }
    
    private String getXMLString(String str)
    {
    	str = str.replace("&amp;", "&" );
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        str = str.replace("&apos;", "'");
        str = str.replace("&quot;", "\"");

        return str;
    }
    
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
}

