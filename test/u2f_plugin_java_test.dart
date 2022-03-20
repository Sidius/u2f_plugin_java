import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:u2f_plugin_java/u2f_plugin_java.dart';

void main() {
  const MethodChannel channel = MethodChannel('u2f_plugin_java');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await U2fPluginJava.platformVersion, '42');
  });
}
