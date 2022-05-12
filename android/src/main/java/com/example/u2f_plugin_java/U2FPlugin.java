package com.example.u2f_plugin_java;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.u2f_plugin_java.model.Device;
import com.example.u2f_plugin_java.utils.Settings;
import com.yubico.yubikit.android.YubiKitManager;
import com.yubico.yubikit.android.transport.nfc.NfcConfiguration;
import com.yubico.yubikit.android.transport.nfc.NfcNotAvailable;
import com.yubico.yubikit.android.transport.nfc.NfcYubiKeyDevice;
import com.yubico.yubikit.android.transport.usb.UsbConfiguration;
import com.yubico.yubikit.android.transport.usb.UsbYubiKeyDevice;
import com.yubico.yubikit.core.application.ApplicationNotAvailableException;
import com.yubico.yubikit.core.smartcard.Apdu;
import com.yubico.yubikit.core.smartcard.ApduException;
import com.yubico.yubikit.core.smartcard.SmartCardConnection;
import com.yubico.yubikit.core.smartcard.SmartCardProtocol;

import java.io.IOException;
import java.util.Arrays;

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
    private NfcYubiKeyDevice nfcYubiKeyDevice;
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
        if (call.method.equals(Settings.START_USB_DISCOVERY)) {
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
        } else if (call.method.equals(Settings.START_NFC_DISCOVERY)) {
            try {
                yubiKitManager.startNfcDiscovery(new NfcConfiguration(), activity, device -> {

                    nfcYubiKeyDevice = device;
//                    this.device = new Device();
//                    this.device.setDeviceId(nfcYubiKeyDevice.get().getDeviceId());
//                    this.device.setDeviceClass(usbYubiKeyDevice.getUsbDevice().getDeviceClass());
//                    this.device.setDeviceName(usbYubiKeyDevice.getUsbDevice().getDeviceName());
//                    this.device.setDeviceProtocol(usbYubiKeyDevice.getUsbDevice().getDeviceProtocol());
//                    this.device.setDeviceSubclass(usbYubiKeyDevice.getUsbDevice().getDeviceSubclass());
//                    this.device.setManufacturerName(usbYubiKeyDevice.getUsbDevice().getManufacturerName());
//                    this.device.setSerialNumber(usbYubiKeyDevice.getUsbDevice().getSerialNumber());
//                    this.device.setVendorId(usbYubiKeyDevice.getUsbDevice().getVendorId());

                    result.success(true);
                });
            } catch (NfcNotAvailable e) {
                if (e.isDisabled()) {
                    // show a message that user needs to turn on NFC for this feature
                    result.success("NFC DISABLED");
                } else {
                    // NFC is not available so this feature does not work on this device
                    result.success("ERROR");
                }
            }
        } else if (call.method.equals(Settings.OPEN_CONNECTION)) {
            if (usbYubiKeyDevice != null) {
                usbYubiKeyDevice.requestConnection(SmartCardConnection.class, response -> {
                    // The result is a Result<SmartCardConnection, IOException>, which represents either a successful connection, or an error.
                    try {
                        SmartCardConnection connection = response.getValue();  // This may throw an IOException
                        // The SmartCardProtocol offers a the ability of sending APDU-based smartcard commands
                        SmartCardProtocol protocol = new SmartCardProtocol(connection);
                        byte[] aid = new byte[] {(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x08};
                        protocol.select(aid);  // Select a smartcard application (this may throw an ApplicationNotFoundException)
                        byte[] bytes = protocol.sendAndReceive(new Apdu(0x00, 0xA4, 0x00, 0x00, null));
                        result.success(Arrays.toString(bytes));
                    } catch(IOException | ApplicationNotAvailableException | ApduException e) {
                        result.success("ERROR");
                        // Handle errors
                    }
                });
            }
        } else if (call.method.equals(Settings.STOP_NFC_DISCOVERY)) {
            yubiKitManager.stopUsbDiscovery();
        } else if (call.method.equals(Settings.STOP_USB_DISCOVERY)) {
            yubiKitManager.stopUsbDiscovery();
        } else if (call.method.equals(Settings.GET_USB_DEVICE)) {
            result.success(Device.serialize(device));
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
