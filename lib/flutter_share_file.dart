import 'dart:async';
import 'dart:io' show Platform;
import 'package:flutter/services.dart';

class FlutterShareFile {
  static const MethodChannel _channel =
      const MethodChannel('flutter_share_file');

  static Future<bool> share(
      String path, String fileName, ShareFileType shareFileType,
      [String message = '']) async {
    bool result;
    if (Platform.isAndroid) {
      // For Android send only the file name of image in temporary Directory

      String fileType;
      switch (shareFileType) {
        case ShareFileType.file:
          fileType = 'file';
          break;
        case ShareFileType.image:
          fileType = 'image/png';
          break;
        case ShareFileType.pdf:
          fileType = 'application/pdf';
          break;
        default:
          fileType = 'file';
      }
      result = await _channel.invokeMethod('shareimage',
          {"fileName": fileName, "message": message, 'type': fileType});
    } else if (Platform.isIOS) {
      String fileType;
      switch (shareFileType) {
        case ShareFileType.file:
          fileType = 'file';
          break;
        case ShareFileType.image:
          fileType = 'image';
          break;
        case ShareFileType.pdf:
          fileType = 'pdf';
          break;
        default:
          fileType = 'file';
      }

      // For iOS send the full path of image in temporary Directory
      final Map<String, dynamic> params = <String, dynamic>{
        'text': path + '/' + fileName,
        'message': message,
        'type': fileType
      };
      result = await _channel.invokeMethod('shareimage', params);
    } else {
      result = false;
    }
    return result;
  }
}

enum ShareFileType {
  image,
  file,
  pdf,
}
