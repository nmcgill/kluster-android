package com.cs446.kluster.net;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import com.cs446.kluster.models.AuthUser;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.cs446.kluster.models.User;

public interface KlusterService {
	
	@GET("/auth")
	void getAuth(@Header("Authorization") String credentials, Callback<AuthUser> cb);
	
	@POST("/users")
	void createUser(@Body User user, Callback<User> cb);
	
	@GET("/photos/{photo}")
	void getPhoto(@Path("photo") String photoid, Callback<Photo> cb);
	
	@GET("/photos")
	void getPhotos(@Query("eventIds") String eventids, Callback<List<Photo>> cb);
	
	@Multipart
	@POST("/photos")
	void createPhoto(@Part("image") TypedFile photo, @Part("loc") TypedString location, @Part("time") TypedString time, @Part("tags[0]") TypedString tag1, @Part("tags[1]") TypedString tag2, @Part("tags[2]") TypedString tag3, Callback<Photo> cb);
	
	@GET("/events/{event}")
	void getEvent(@Path("event") String eventid, Callback<Event> cb);

	@GET("/events")
	void getEvents(Callback<List<Event>> cb);
	
	@GET("/events")
	void getEvents(@Query("ll") String ll, Callback<List<Event>> cb);
	
	@GET("/events")
	void getEvents(@Query("ids") String eventids, @Query("limit") String limit, @Query("bounds") String bounds, Callback<List<Event>> cb);
}