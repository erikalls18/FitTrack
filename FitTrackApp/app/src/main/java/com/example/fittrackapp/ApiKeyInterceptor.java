package com.example.fittrackapp;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiKeyInterceptor implements Interceptor {
    private String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;
        request = original.newBuilder()
                .header("X-Api-Key", apiKey)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
