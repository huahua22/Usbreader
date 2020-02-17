package com.xwr.usbreader.dev.usbapi;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

/**
 * Create by xwr on 2020/2/13
 * Describe:
 */
public class UsbApi {
  static {
    System.loadLibrary("cp");
  }

  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native static String stringFromJNI();

  public static native long Reader_Init(UsbDeviceConnection deviceConnection,
    UsbEndpoint usbEpIn,
    UsbEndpoint usbEPOut);

  public static native long Test_Beep();

  public static native long ICC_Reader_PowerOn(byte slot, byte[] atr);

  public static native long ICC_Reader_PowerOff(byte slot);

  public static native long ICC_Reader_GetStatus(byte slot);

  public static native long ICC_Reader_Application(byte slot, long command_len, byte[] apdu, byte[] respond_apdu);

  public static native long ICC_Reader_GetDevID(byte[] device_id);

  public static native long Lib_Version(byte[] Info);

  public static native long Syn_Read_Card(byte[] cardInfo);


  public static native long Syn_Find_Card();

  public static native long Syn_Get_Card(byte[] cardInfo);
}
