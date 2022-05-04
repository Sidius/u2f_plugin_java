import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:u2f_plugin_java/models/device.dart';
import 'package:u2f_plugin_java/utils/settings.dart';

class U2FPlugin {
  static const MethodChannel _channel = MethodChannel(Settings.startClassU2F);

  static Future<Device?> get getUsbDevice async {
    final String? deviceJson = await _channel.invokeMethod(Settings.getUSBDevice);
    //print("DeviceJSON " + deviceJson!);
    if (deviceJson != null) {
      return Device.fromJson(json.decode(deviceJson));
    }
    return null;
  }

  static Future<bool?> startUSBDiscovery() async {
    final bool? response = await _channel.invokeMethod(Settings.startUSBDiscovery);
    return response;
  }

  static stopUSBDiscovery() async {
    await _channel.invokeMethod(Settings.stopUSBDiscovery);
  }
}