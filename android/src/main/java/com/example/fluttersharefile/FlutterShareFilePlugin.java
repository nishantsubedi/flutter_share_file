package com.example.fluttersharefile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.util.HashMap;

import io.flutter.app.FlutterActivity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import androidx.core.content.FileProvider;

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
      Object arguments = call.arguments;
      HashMap<String, String> argsMap = (HashMap<String, String>) arguments;
      String fileName = argsMap.get("fileName");
      String message = argsMap.get("message");
      shareFile(fileName, message);
    } else {
      result.notImplemented();
    }
  }

  private void shareFile(String fileName, String message) {
    File imageFile = new File(instance.activeContext().getCacheDir(), fileName);
    Uri contentUri = FileProvider.getUriForFile(instance.activeContext(), "com.example.fluttersharefile", imageFile);
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("image/png");
    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
    instance.activity().startActivity(Intent.createChooser(shareIntent, "Share image using"));
}
}
