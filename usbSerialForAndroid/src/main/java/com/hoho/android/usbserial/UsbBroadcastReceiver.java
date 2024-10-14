package com.hoho.android.usbserial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.util.Log;

public class UsbBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "UsbBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "USB Device onReceive: " + action);
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (device != null) {
                onUsbDeviceAttached(device);
            }
        } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (device != null) {
                onUsbDeviceDetached(device);
            }
        }
    }

    private void onUsbDeviceAttached(UsbDevice device) {
        Log.d(TAG, "USB Device Attached: " + device.getDeviceName());
        identifyUsbDevice(device);
    }

    private void onUsbDeviceDetached(UsbDevice device) {
        Log.d(TAG, "USB Device Detached: " + device.getDeviceName());
    }

    /**
     * USB_CLASS_AUDIO (1): 音频设备USB_CLASS_COMM
     * (2): 通信设备USB_CLASS_HID
     * (3): 人机交互设备（例如键盘和鼠标）USB_CLASS_PHYSICAL
     * (5): 物理设备USB_CLASS_IMAGE
     * (6): 图像设备USB_CLASS_PRINTER
     * (7): 打印机USB_CLASS_MASS_STORAGE
     * (8): 大容量存储设备USB_CLASS_HUB
     * (9): 集线器设备USB_CLASS_DATA
     * (10): 数据设备USB_CLASS_SMART_CARD
     * (11): 智能卡USB_CLASS_VIDEO
     * (14): 视频设备USB_CLASS_WIRELESS_CONTROLLER (224): 无线控制设备
     * @param device
     */
    private void identifyUsbDevice(UsbDevice device) {
        int deviceClass = device.getDeviceClass();
        switch (deviceClass) {
            case 1: // USB_CLASS_AUDIO
                Log.d(TAG, "Device type: Audio");
                break;
            case 2: // USB_CLASS_COMM
                Log.d(TAG, "Device type: Communication");
                break;
            case 3: // USB_CLASS_HID
                Log.d(TAG, "Device type: Human Interface Device");
                break;
            case 8: // USB_CLASS_MASS_STORAGE
                Log.d(TAG, "Device type: Mass Storage");
                break;
            case 224: // USB_CLASS_WIRELESS_CONTROLLER
                Log.d(TAG, "Device type: Wireless Controller");
                break;
            default:
                Log.d(TAG, "Device type: Other (Class: " + deviceClass + ")");
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "Manufacturer: " + device.getManufacturerName());
            Log.d(TAG, "Product: " + device.getProductName());
        }
    }
}

