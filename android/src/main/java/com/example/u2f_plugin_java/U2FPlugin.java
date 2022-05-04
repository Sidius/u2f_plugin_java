package com.example.u2f_plugin_java;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.u2f_plugin_java.model.Device;
import com.example.u2f_plugin_java.utils.Settings;
import com.yubico.yubikit.android.YubiKitManager;
import com.yubico.yubikit.android.transport.usb.UsbConfiguration;
import com.yubico.yubikit.android.transport.usb.UsbYubiKeyDevice;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** U2FPlugin */
public class U2FPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private Activity activity;
    private YubiKitManager yubiKitManager;
    private UsbYubiKeyDevice usbYubiKeyDevice;
    private Device device;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), Settings.START_CLASS_U2F);
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        yubiKitManager = new YubiKitManager(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getAuth")) {
            yubiKitManager.startUsbDiscovery(new UsbConfiguration(), device -> {
                if(!device.hasPermission()) {
                    result.success("NOT");
                }
                result.success("WORKING");

                device.setOnClosed(() -> {
                });
            });
            //result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals(Settings.START_USB_DISCOVERY)) {
            yubiKitManager.startUsbDiscovery(new UsbConfiguration(), device -> {
                if(!device.hasPermission()) {
                    result.success(false);
                }
                usbYubiKeyDevice = device;
                this.device = new Device();
                this.device.setDeviceId(usbYubiKeyDevice.getUsbDevice().getDeviceId());
                this.device.setDeviceClass(usbYubiKeyDevice.getUsbDevice().getDeviceClass());
                this.device.setDeviceName(usbYubiKeyDevice.getUsbDevice().getDeviceName());
                this.device.setDeviceProtocol(usbYubiKeyDevice.getUsbDevice().getDeviceProtocol());
                this.device.setDeviceSubclass(usbYubiKeyDevice.getUsbDevice().getDeviceSubclass());
                this.device.setManufacturerName(usbYubiKeyDevice.getUsbDevice().getManufacturerName());
                this.device.setSerialNumber(usbYubiKeyDevice.getUsbDevice().getSerialNumber());
                this.device.setVendorId(usbYubiKeyDevice.getUsbDevice().getVendorId());

                result.success(true);

                device.setOnClosed(() -> {
                    // Do something when the YubiKey is removed
                });
            });
        } else if (call.method.equals(Settings.STOP_USB_DISCOVERY)) {
            yubiKitManager.stopUsbDiscovery();
        } else if (call.method.equals(Settings.GET_USB_DEVICE)) {
            result.success(Device.serialize(device));
        } else if (call.method.equals("checkAuth")) {

        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }
}
