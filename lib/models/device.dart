
class Device {

  String? deviceName;
  String? manufacturerName;
  String? productName;
  String? version;
  String? serialNumber;
  int deviceId;
  int vendorId;
  int productId;
  int deviceClass;
  int deviceSubclass;
  int deviceProtocol;
  int configurationCount;

  /*

  final String deviceName = '';
  final String manufacturerName = '';
  final String productName = '';
  final String version = '';
  final String serialNumber = '';
  final int deviceId = 0;
  final int vendorId = 0;
  final int productId = 0;
  final int deviceClass = 0;
  final int deviceSubclass = 0;
  final int deviceProtocol = 0;
  final int configurationCount = 0;

  String deviceName;
  String manufacturerName;
  String productName;
  String version;
  String serialNumber;
  int deviceId;
  int vendorId;
  int productId;
  int deviceClass;
  int deviceSubclass;
  int deviceProtocol;
  int configurationCount;
  */

  Device({
    this.deviceName = '',
    this.manufacturerName = '',
    this.productName = '',
    this.version = '',
    this.serialNumber = '',
    this.deviceId = 0,
    this.vendorId = 0,
    this.productId = 0,
    this.deviceClass = 0,
    this.deviceSubclass = 0,
    this.deviceProtocol = 0,
    this.configurationCount = 0,
  });

  factory Device.fromJson(Map<String, dynamic> json) {
    return Device(
      deviceName: json['deviceName'] as String?,
      manufacturerName: json['manufacturerName'] as String?,
      productName: json['productName'] as String?,
      version: json['version'] as String?,
      serialNumber: json['serialNumber'] as String?,
      deviceId: json['deviceId'] as int,
      vendorId: json['vendorId'] as int,
      productId: json['productId'] as int,
      deviceClass: json['deviceClass'] as int,
      deviceSubclass: json['deviceSubclass'] as int,
      deviceProtocol: json['deviceProtocol'] as int,
      configurationCount: json['configurationCount'] as int,
    );
  }

  @override
  String toString() {
    return 'Device{deviceName: $deviceName, manufacturerName: $manufacturerName, productName: $productName, version: $version, serialNumber: $serialNumber, deviceId: $deviceId, vendorId: $vendorId, productId: $productId, deviceClass: $deviceClass, deviceSubclass: $deviceSubclass, deviceProtocol: $deviceProtocol, configurationCount: $configurationCount}';
  }
}