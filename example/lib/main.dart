import 'package:flutter/material.dart';

import 'package:flutter_share_file/flutter_share_file.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
            child: new Column(
          children: <Widget>[
            new RaisedButton(
              onPressed: () async {
                File f =
                    await ImagePicker.pickImage(source: ImageSource.gallery);
                FlutterShareFile.shareImage(f.path, "image.png");
              },
              child: new Text("share image"),
            ),
          ],
        )),
      ),
    );
  }

}