package com.bonesignited.iridescence;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {
    public static String getRandomFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String s = sdf.format(new Date());
        Random random = new Random();
        int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
        return num + s + ".jpg";
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.exists()) {
            file.mkdir();
        } else {
            Log.e("FILE STORAGE", "Directory not created");
        }
        return file;
    }

    public static void setWallpaper(Context context, String path, String authority) {
        if (context == null || TextUtils.isEmpty(path) || TextUtils.isEmpty(authority)) {
            return;
        }
        Uri uriPath = getUriWithPath(context, path, authority);
        Intent intent;

        // 针对EMUI
        if (RomUtil.isHuaweiRom()) {
            try {
                ComponentName componentName = new ComponentName("com.android.gallery3d", "com.android.gallery3d.app.Wallpaper");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriPath, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    WallpaperManager.getInstance(context.getApplicationContext()).setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // 针对MIUI
        } else if (RomUtil.isMiuiRom()) {
            try {
                ComponentName componentName = new ComponentName("com.android.thememanager", "com.android.thememanager.activity.WallpaperDetailActivity");
                intent = new Intent("miui.intent.action.START_WALLPAPER_DETAIL");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriPath, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    WallpaperManager.getInstance(context.getApplicationContext()).setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // 其他
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                context.startActivity(WallpaperManager.getInstance(context.getApplicationContext())
                        .getCropAndSetWallpaperIntent(uriPath));
            } else {
                try {
                    WallpaperManager.getInstance(context.getApplicationContext()).setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static Uri getUriWithPath(Context context, String filepath, String authority) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0以上的读取文件uri要用这种方式了
            return FileProvider.getUriForFile(context.getApplicationContext(), authority, new File(filepath));
        } else {
            return Uri.fromFile(new File(filepath));
        }
    }
}
