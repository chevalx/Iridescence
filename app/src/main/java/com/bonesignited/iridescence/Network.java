package com.bonesignited.iridescence;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network{
    public static void sendRequest(String url, okhttp3.Callback callback) {

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(callback);

    }
}
