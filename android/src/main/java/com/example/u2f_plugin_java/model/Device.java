package com.example.u2f_plugin_java.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class Device implements Serializable {
    private String deviceName;
    private String manufacturerName;
    private String productName;
    private String version;
    private String serialNumber;
    private int deviceId;
    private int vendorId;
    private int productId;
    private int deviceClass;
    private int deviceSubclass;
    private int deviceProtocol;
    private int configurationCount;

    public Device() {

    }

    public Device(String deviceName, String manufacturerName, String productName, String version,
                  String serialNumber, int deviceId, int vendorId, int productId, int deviceClass,
                  int deviceSubclass, int deviceProtocol, int configurationCount) {
        this.deviceName = deviceName;
        this.manufacturerName = manufacturerName;
        this.productName = productName;
        this.version = version;
        this.serialNumber = serialNumber;
        this.deviceId = deviceId;
        this.vendorId = vendorId;
        this.productId = productId;
        this.deviceClass = deviceClass;
        this.deviceSubclass = deviceSubclass;
        this.deviceProtocol = deviceProtocol;
        this.configurationCount = configurationCount;
    }

    public static String serialize(Device device) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(device);
    }

    @NonNull
    @Override
    public String toString() {
        return "Device{" +
                "deviceName='" + deviceName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", productName='" + productName + '\'' +
                ", version='" + version + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", deviceId=" + deviceId +
                ", vendorId=" + vendorId +
                ", productId=" + productId +
                ", deviceClass=" + deviceClass +
                ", deviceSubclass=" + deviceSubclass +
                ", deviceProtocol=" + deviceProtocol +
                ", configurationCount=" + configurationCount +
                '}';
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDeviceClass(int deviceClass) {
        this.deviceClass = deviceClass;
    }

    public void setDeviceSubclass(int deviceSubclass) {
        this.deviceSubclass = deviceSubclass;
    }

    public void setDeviceProtocol(int deviceProtocol) {
        this.deviceProtocol = deviceProtocol;
    }

    public void setConfigurationCount(int configurationCount) {
        this.configurationCount = configurationCount;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getProductName() {
        return productName;
    }

    public String getVersion() {
        return version;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getProductId() {
        return productId;
    }

    public int getDeviceClass() {
        return deviceClass;
    }

    public int getDeviceSubclass() {
        return deviceSubclass;
    }

    public int getDeviceProtocol() {
        return deviceProtocol;
    }

    public int getConfigurationCount() {
        return configurationCount;
    }
}
