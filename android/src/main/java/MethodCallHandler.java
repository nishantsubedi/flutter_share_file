package com.example.fluttersharefile;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.Map;

class MethodCallHandler implements MethodChannel.MethodCallHandler {

  private Share share;

  MethodCallHandler(Share share) {
    this.share = share;
  }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
    if (call.method.equals("shareimage")) {
        if (!(call.arguments instanceof Map)) {
            throw new IllegalArgumentException("Map argument expected");
        }
        share.shareFile((String) call.argument("fileName"), (String) call.argument("message"), (String) call.argument("type"));
        result.success(null);
        } else {
        result.notImplemented();
        }
    }
}