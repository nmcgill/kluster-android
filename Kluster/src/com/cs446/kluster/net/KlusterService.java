package com.cs446.kluster.net;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import com.cs446.kluster.models.AuthUser;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.cs446.kluster.models.User;
import com.google.gson.JsonObject;

public interface KlusterService {
	
	@GET("/auth")
	void getAuth(@Header("Authorization") String credentials, Callback<AuthUser> cb);
	
	@POST("/users")
	void createUser(@Body User user, Callback<User> cb);
	
	@GET("/users/{userid}")
	void getUser(@Path("userid") String userid, Callback<AuthUser> cb);
	
	@GET("/photos/{photo}")
	void getPhoto(@Path("photo") String photoid, Callback<Photo> cb);
	
	@GET("/photos")
	void getPhotos(@Query("eventIds") String eventids, Callback<List<Photo>> cb);
	
	@GET("/photos")
	void getPhotosByIds(@Query("ids") String ids, Callback<List<Photo>> cb);
	
	@GET("/photos")
	void getPhotosByUserIds(@Query("userIds") String userids, Callback<List<Photo>> cb);
	
	@Multipart
	@POST("/photos")
	void createPhoto(@Part("image") TypedFile photo, @Part("loc") TypedString location, @Part("time") TypedString time, @Part("tags[0]") TypedString tag1, @Part("tags[1]") TypedString tag2, @Part("tags[2]") TypedString tag3, Callback<Photo> cb);
	
	@GET("/events/{event}")
	void getEvent(@Path("event") String eventid, Callback<Event> cb);

	@GET("/events")
	void getEvents(Callback<List<Event>> cb);
	
	@GET("/events")
	void getEvents(@Query("ll") String ll, @Query("radius_meters") String radius, Callback<List<Event>> cb);
	
	@GET("/events")
	void getEvents(@Query("ids") String eventids, @Query("limit") String limit, @Query("bounds") String bounds, Callback<List<Event>> cb);

	@Headers("Content-type: application/json")
	@POST("/photos/{photo}/rate")
	void ratePhotoUp(@Path("photo") String photoid, @Body JsonObject upValue, Callback<Response> cb);

	@Headers("Content-type: application/json")
	@POST("/photos/{photo}/rate")
	void ratePhotoDown(@Path("photo") String photoid, @Body JsonObject downValue, Callback<Response> cb);
}