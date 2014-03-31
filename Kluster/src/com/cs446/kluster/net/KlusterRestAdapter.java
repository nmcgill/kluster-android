package com.cs446.kluster.net;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.data.serialize.AuthUserAdapter;
import com.cs446.kluster.data.serialize.EventAdapter;
import com.cs446.kluster.data.serialize.PhotoAdapter;
import com.cs446.kluster.data.serialize.UserAdapter;
import com.cs446.kluster.models.AuthUser;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.cs446.kluster.models.User;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class KlusterRestAdapter extends RestAdapter.Builder {
	
	public KlusterRestAdapter() {
		
		Gson gson = new GsonBuilder()
	    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
	    .registerTypeAdapter(Event.class, new EventAdapter())
	    .registerTypeAdapter(Photo.class, new PhotoAdapter())
	    .registerTypeAdapter(AuthUser.class, new AuthUserAdapter())
	    .registerTypeAdapter(User.class, new UserAdapter())
	    .create();
		
        ConfigManager config = KlusterApplication.getInstance().getConfigManager();
        String url = config.getProperty(ConfigManager.PROP_URL);
        
        setEndpoint(url);
        setConverter(new GsonConverter(gson));
	}

}
