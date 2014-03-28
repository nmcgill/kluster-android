package com.cs446.kluster.net;

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.net.http.AuthRequest;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class PhotoRequest extends AuthRequest {

    final private static String ENDPOINT_PHOTOS = "/photos";

    public PhotoRequest(String url) {
        super(Method.GET, url);
    }

    public static PhotoRequest create(String photoId) {
        ConfigManager config = KlusterApplication.getInstance().getConfigManager();
        String url = config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_PHOTOS + "/" + photoId;
        return new PhotoRequest(url);
    }
}