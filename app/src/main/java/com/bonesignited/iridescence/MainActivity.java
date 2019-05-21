package com.bonesignited.iridescence;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoadListener {

    private List<Picture> mPictureList = new ArrayList<>();
    private PictureAdapter adapter;
    private RecyclerView recyclerView;
    private LoadTask loadTask;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadTask = new LoadTask();
        loadTask.setLoadListener(this);
        loadTask.execute(mPictureList);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onReceiveMsg(String msg) {
        adapter = new PictureAdapter(this, mPictureList);
        if (msg.equals("c")) {
            recyclerView.setAdapter(adapter);
        }
    }


    static class LoadTask extends AsyncTask<List<Picture>, Integer, Boolean> {

        private LoadListener loadListener;

        public void setLoadListener(LoadListener loadListener) {
            this.loadListener = loadListener;
        }

        @Override
        protected Boolean doInBackground(List<Picture>... lists) {
//            String interfaceUrl = "https://bing.biturl.top/?resolution=1920&format=json&index=1&mkt=zh-CN";
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            for (int i = 1; i < 6; i++) {
                StringBuilder sb = new StringBuilder("https://bing.biturl.top/?resolution=1920&format=json&");
                sb.append("index=").append(String.valueOf(i)).append("&mkt=zh-CN");

                final Request request = new Request.Builder().url(sb.toString()).build();
                final Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    Picture picture = gson.fromJson(response.body().string(), Picture.class);
                    lists[0].add(picture);
                    Log.d("MainActivity", "onResponse: 获得数据");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (lists[0].size() > 0) {
                return true;
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(MyApplication.getContext(), "已加载到 mPictureList 中", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MyApplication.getContext(), "加载失败", Toast.LENGTH_LONG).show();
            }
            loadListener.onReceiveMsg("c");
        }
    }
}