package com.bonesignited.iridescence;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bonesignited.iridescence.Constant.UPDATE_PIC_LIST;

public class MainActivity extends AppCompatActivity {

    private List<Picture> mPictureList = new ArrayList<>();
    private PictureAdapter adapter;
    private RecyclerView recyclerView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PIC_LIST:
                    adapter = new PictureAdapter(MyApplication.getContext(), mPictureList);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        getPictureUrls();
    }

    private void getPictureUrls() {
        String api = "http://www.bing.com/HPImageArchive.aspx?format=js&n=8&idx=1";
        HttpUtil.sendOkHttpRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainApplication", "fail to fetch pictures url");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("json", responseData);
                JSONObject jsonObject = JSON.parseObject(responseData);
                JSONArray jsonArray = jsonObject.getJSONArray("images");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String startDate = jo.getString("startdate");
                    String fullStartDate = jo.getString("fullstartdate");
                    String endDate = jo.getString("enddate");
                    String url = jo.getString("url");
                    String copyright = jo.getString("copyright");
                    String copyrightLink = jo.getString("copyrightLink");

                    String imgUrl = "http://www.bing.com" + url.replace("1920x1080", "1080x1920");
                    mPictureList.add(new Picture(startDate, fullStartDate, endDate, imgUrl, copyright, copyrightLink));
                }
                Message msg = new Message();
                msg.what = UPDATE_PIC_LIST;
                handler.sendMessage(msg);
            }
        });
    }
}