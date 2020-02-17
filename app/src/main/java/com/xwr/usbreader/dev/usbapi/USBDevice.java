package com.xwr.usbreader.dev.usbapi;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

/**
 * Create by xwr on 2020/2/11
 * Describe:
 */
public class USBDevice {
  public static UsbDeviceConnection mDeviceConnection = null;
  //代表一个接口的某个节点的类:写数据节点
  public static UsbEndpoint usbEpOut = null;
  //代表一个接口的某个节点的类:读数据节点
  public static UsbEndpoint usbEpIn = null;
}
