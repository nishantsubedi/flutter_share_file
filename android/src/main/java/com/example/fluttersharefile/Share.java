package com.example.fluttersharefile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import androidx.core.content.FileProvider;


class Share {
    private Activity activity;

    Share(Activity activity) {
        this.activity = activity;
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }

    void shareFile(String fileName, String message, String type) {
        File imageFile = new File(activity.getApplicationContext().getCacheDir(), fileName);
        Uri contentUri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".contentprovider", imageFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType(type);
        if(message != null || !message.isEmpty()) {
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        shareIntent.setType("text/plain");
        }
        activity.startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }
}