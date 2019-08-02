#import "FlutterShareFilePlugin.h"

@implementation FlutterShareFilePlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* shareChannel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_share_file"
            binaryMessenger:[registrar messenger]];

[shareChannel setMethodCallHandler:^(FlutterMethodCall *call, FlutterResult result) {
    if ([@"shareimage" isEqualToString:call.method]) {
      NSDictionary *arguments = [call arguments];
      NSString *shareText = arguments[@"text"];
      NSString *shareType = arguments[@"type"];
      NSString *message = arguments[@"message"];

      if (shareText.length == 0) {
        result(
            [FlutterError errorWithCode:@"error" message:@"Non-empty text expected" details:nil]);
        return;
      }

      NSNumber *originX = arguments[@"originX"];
      NSNumber *originY = arguments[@"originY"];
      NSNumber *originWidth = arguments[@"originWidth"];
      NSNumber *originHeight = arguments[@"originHeight"];

      CGRect originRect;
      if (originX != nil && originY != nil && originWidth != nil && originHeight != nil) {
        originRect = CGRectMake([originX doubleValue], [originY doubleValue],
                                [originWidth doubleValue], [originHeight doubleValue]);
      }

        if ([shareType isEqualToString:@"text"]) {
            [self share:@[shareText]
         withController:[UIApplication sharedApplication].keyWindow.rootViewController
               atSource:originRect];
            result(nil);
        } else if([shareType isEqualToString:@"file"]) {
            NSURL *url = [NSURL fileURLWithPath:shareText];
            [self share:@[url]
         withController:[UIApplication sharedApplication].keyWindow.rootViewController
               atSource:originRect];
            result(nil);
        } else if ([shareType isEqualToString:@"image"]) {
            UIImage *image = [UIImage imageWithContentsOfFile:shareText];
            [self share:@[image, message]
         withController:[UIApplication sharedApplication].keyWindow.rootViewController
               atSource:originRect];
        }
    } else {
      result(FlutterMethodNotImplemented);
    }
  }];
}

+ (void)share:(id)sharedItems
    withController:(UIViewController *)controller
          atSource:(CGRect)origin {
  UIActivityViewController *activityViewController =
      [[UIActivityViewController alloc] initWithActivityItems:sharedItems
                                        applicationActivities:nil];
  activityViewController.popoverPresentationController.sourceView = controller.view;
  if (!CGRectIsEmpty(origin)) {
    activityViewController.popoverPresentationController.sourceRect = origin;
  }
  [controller presentViewController:activityViewController animated:YES completion:nil];
}

@end