import 'dart:async';
import 'dart:io' show Platform;
import 'package:flutter/services.dart';

class FlutterShareFile {
  static const MethodChannel _channel =
      const MethodChannel('flutter_share_file');

  static Future<bool> shareImage(String path, String fileName) async {
    bool result;
    if (Platform.isAndroid) {
      result = await _channel.invokeMethod('shareimage', fileName);
    } else if (Platform.isIOS) {
      final Map<String, dynamic> params = <String, dynamic>{
        'text': path + '/' + fileName,
        'type': 'image'
      };
      result = await _channel.invokeMethod('shareimage', params);
    } else {
      result = false;
    }
    return result;
  }
}
