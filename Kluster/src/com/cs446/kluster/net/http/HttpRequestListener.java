package com.cs446.kluster.net.http;

/**
 * Created by Marlin Gingerich on 2014-03-08.
 */
public interface HttpRequestListener<Result> {

    public void onStart();

    public void onComplete();

    public void onError(Exception e);

    public void onSuccess(Result result);
}