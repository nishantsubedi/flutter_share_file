package com.example.fluttersharefile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

import io.flutter.app.FlutterActivity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.support.v4.content.FileProvider;

/** FlutterShareFilePlugin */
public class FlutterShareFilePlugin extends FlutterActivity implements MethodCallHandler {
  /** Plugin registration. */
  private static Registrar instance;

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_share_file");
    channel.setMethodCallHandler(new FlutterShareFilePlugin());
    instance = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("shareimage")) {
      shareFile((String) call.arguments);
    } else {
      result.notImplemented();
    }
  }

  private void shareFile(String path) {
    File imageFile = new File(instance.activeContext().getCacheDir(), path);
    Uri contentUri = FileProvider.getUriForFile(instance.activeContext(), "com.example.fluttersharefile", imageFile);
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("image/png");
    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
    instance.activity().startActivity(Intent.createChooser(shareIntent, "Share image using"));
}
}
