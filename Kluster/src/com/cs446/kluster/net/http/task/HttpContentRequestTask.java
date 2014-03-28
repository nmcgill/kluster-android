package com.cs446.kluster.net.http.task;

import android.os.AsyncTask;
import android.os.Handler;

import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.Request;
import com.cs446.kluster.net.http.Response;
import com.cs446.kluster.data.StorageAdapter;
import com.cs446.kluster.data.serialize.Serializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class HttpContentRequestTask<Result> extends AbstractHttpRequestTask<Result> {

    private StorageAdapter<Result> mStorageAdapter = null;

    public HttpContentRequestTask(HttpRequestListener<Result> listener, Serializer<Result> serializer, StorageAdapter<Result> storageAdapter) {
        super(listener, serializer);
        this.mStorageAdapter = storageAdapter;
    }

    public HttpContentRequestTask(Serializer<Result> serializer, StorageAdapter<Result> storageAdapter) {
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
                        if (HttpContentRequestTask.this.mListener != null) {
                            HttpContentRequestTask.this.mListener.onComplete();
                            if (HttpContentRequestTask.this.mError != null) {
                                HttpContentRequestTask.this.mListener.onError(HttpContentRequestTask.this.mError);
                                return;
                            }
                            HttpContentRequestTask.this.mListener.onSuccess(result);
                        }
                    }
                });
            }
        });
    }

    public Result execute(Request request) {
        return this.doWork(request);
    }

    protected Result parse(Response response) throws IOException {
        if (this.mSerializer != null) {
            InputStreamReader reader = new InputStreamReader(response.getBody());
            return this.mSerializer.read(reader);
        }
        return null;
    }

    protected void store(Result result) {
        if (this.mStorageAdapter != null) {
            this.mStorageAdapter.upsert(result);
        }
    }

    private Result doWork(Request request) {
        HttpURLConnection connection = null;
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

        return null;
    }
}