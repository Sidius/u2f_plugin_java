import 'package:flutter/services.dart';

abstract class U2FPluginCore {
  static const MethodChannel _channel = MethodChannel('U2F');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getAuth');
    return version;
  }
}