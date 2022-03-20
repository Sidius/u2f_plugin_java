
import 'dart:async';

import 'package:flutter/services.dart';

class U2fPluginJava {
  static const MethodChannel _channel = MethodChannel('u2f_plugin_java');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
