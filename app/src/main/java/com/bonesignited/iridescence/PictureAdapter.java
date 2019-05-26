package com.bonesignited.iridescence;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private Context context;
    private List<Picture> pictureList;

    public PictureAdapter(Context context, List<Picture> pictureList) {
        this.context = context;
        this.pictureList = pictureList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView image;
        TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.picture_image);
            description = itemView.findViewById(R.id.description);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.picture_item, viewGroup, false
        );
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.bonesignited.iridescence.IMAGE_DETAIL");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                int position = viewHolder.getAdapterPosition();
                intent.putExtra("url", pictureList.get(position).getUrl());
                intent.putExtra("copyright", pictureList.get(position).getCopyright());
                MyApplication.getContext().startActivity(intent);
            }
        });
//        viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = viewHolder.getAdapterPosition();
//                String url = pictureList.get(position).getUrl();
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .get()
//                        .url(url)
//                        .build();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        //将响应数据转化为输入流数据
//                        InputStream inputStream = response.body().byteStream();
//                        //将输入流数据转化为Bitmap位图数据
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
//                        File file = new File(dir + Util.getRandomFileName());
//                        boolean saveResult = file.createNewFile();
//                        //创建文件输出流对象用来向文件中写入数据
//                        FileOutputStream out = new FileOutputStream(file);
//                        //将bitmap存储为jpg格式的图片
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                        //刷新文件流
//                        out.flush();
//                        out.close();
//                        Uri uri = Uri.fromFile(file);
//                        MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//                    }
//                });
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String url = pictureList.get(i).getUrl();
        String copyright = pictureList.get(i).getCopyright();
        Glide.with(context)
                .load(url)
                .into(viewHolder.image);

        viewHolder.description.setText(copyright);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
