import 'dart:async';
import 'dart:io' show Platform;
import 'package:flutter/services.dart';

class FlutterShareFile {
  static const MethodChannel _channel =
      const MethodChannel('flutter_share_file');

  static Future<bool> shareImage(String path, String fileName, [String message = '']) async {
    bool result;
    if (Platform.isAndroid) {
      // For Android send only the file name of image in temporary Directory
      result = await _channel.invokeMethod(
          'shareimage', {"fileName": fileName, "message": message});
    } else if (Platform.isIOS) {
      // For iOS send the full path of image in temporary Directory
      final Map<String, dynamic> params = <String, dynamic>{
        'text': path + '/' + fileName,
        'message': message,
        'type': 'image'
      };
      result = await _channel.invokeMethod('shareimage', params);
    } else {
      result = false;
    }
    return result;
  }
}
