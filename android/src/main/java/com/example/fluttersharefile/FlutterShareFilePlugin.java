package com.example.fluttersharefile;

import android.app.Activity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** Plugin method host for presenting a share sheet via Intent */
public class FlutterShareFilePlugin implements FlutterPlugin, ActivityAware {

  private static final String CHANNEL = "flutter_share_file";
  private MethodCallHandler handler;
  private Share share;
  private MethodChannel methodChannel;

  public static void registerWith(Registrar registrar) {
    FlutterShareFilePlugin plugin = new FlutterShareFilePlugin();
    plugin.setUpChannel(registrar.activity(), registrar.messenger());
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    setUpChannel(null, binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    methodChannel = null;
    share = null;
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    share.setActivity(binding.getActivity());
  }

  @Override
  public void onDetachedFromActivity() {
    tearDownChannel();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity();
  }

  private void setUpChannel(Activity activity, BinaryMessenger messenger) {
    methodChannel = new MethodChannel(messenger, CHANNEL);
    share = new Share(activity);
    handler = new MethodCallHandler(share);
    methodChannel.setMethodCallHandler(handler);
  }

  private void tearDownChannel() {
    share.setActivity(null);
    methodChannel.setMethodCallHandler(null);
  }
}
