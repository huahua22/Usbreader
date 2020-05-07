package com.xwr.usbreader.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.xwr.usbreader.dev.usbapi.USBDevice;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Create by xwr on 2020/2/10
 * Describe:
 */
public class UsbUtil {
  private static UsbUtil mUsbUtil = null;
  public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
  private static Context mContext;
  //设备列表
  private HashMap<String, UsbDevice> deviceList;
  //USB管理器:负责管理USB设备的类
  private UsbManager manager;
  //找到的USB设备
  private UsbDevice mUsbDevice;
  //代表USB设备的一个接口
  private UsbInterface mInterface;
  private UsbDeviceConnection mDeviceConnection;
  //代表一个接口的某个节点的类:写数据节点
  private UsbEndpoint usbEpOut;
  //代表一个接口的某个节点的类:读数据节点
  private UsbEndpoint usbEpIn;
  //  private Context mContext;
  private PendingIntent mPermissionIntent;

  public static UsbUtil getInstance(Context context) {
    if (mUsbUtil == null) {
      synchronized (UsbUtil.class) {
        mUsbUtil = new UsbUtil();
        mContext = context;
      }
    }
    return mUsbUtil;
  }

  //初始化USB接口
  public void initUsbData() throws InterruptedException {
    // 获取USB设备
    manager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
    //获取到设备列表
    deviceList = manager.getDeviceList();

    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    while (deviceIterator.hasNext()) {
      //             Log.e("ldm", "vid=" + mUsbDevice.getVendorId() + "---pid=" + mUsbDevice.getProductId());
      mUsbDevice = deviceIterator.next();
      Log.e("ldm", "vid=" + mUsbDevice.getVendorId() + "---pid=" + mUsbDevice.getProductId());
      if ((0xffff == mUsbDevice.getVendorId()) && (0xffff == mUsbDevice.getProductId())) {//找到指定设备
        int temp = mUsbDevice.getVendorId();
        break;
      }
      mUsbDevice = null;
    }
    if (mUsbDevice != null) {
      //获取设备接口
      for (int i = 0; i < mUsbDevice.getInterfaceCount(); ) {
        // 一般来说一个设备都是一个接口，你可以通过getInterfaceCount()查看接口的个数
        // 这个接口上有两个端点，分别对应OUT 和 IN
        UsbInterface usbInterface = mUsbDevice.getInterface(i);
        mInterface = usbInterface;
        break;
      }
    }
    if (mInterface != null) {
      //申请权限
      //manager.requestPermission(mUsbDevice, mPermissionIntent);
      // 判断是否有权限
      if (hasPermission(mUsbDevice)) {
        Log.d("xwr", "has usb permission");
        afterGetUsbPermission();
      } else {
        requestPermission(mUsbDevice);
        showTmsg("没有权限");
      }
    } else {
      showTmsg("没有找到设备接口！");
    }

  }

  //判断是否有权限
  public boolean hasPermission(UsbDevice device) {
    return manager.hasPermission(device);
  }

  public void afterGetUsbPermission() {
    Log.d("xwr", "get permission");
    // 打开设备，获取 UsbDeviceConnection 对象，连接设备，用于后面的通讯
    mDeviceConnection = manager.openDevice(mUsbDevice);
    if (mDeviceConnection == null) {
      //return;
    }
    if (mDeviceConnection.claimInterface(mInterface, true)) {
      //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
      if (mInterface.getEndpoint(1) != null) {
        usbEpOut = mInterface.getEndpoint(1);
        usbEpOut.getAddress();
      }
      if (mInterface.getEndpoint(0) != null) {
        usbEpIn = mInterface.getEndpoint(0);
        usbEpIn.getAddress();
      }
      showTmsg("找到设备接口");
      Log.d("Test", "找到设备");
      //            Log.d("Test","test");
      USBDevice.mDeviceConnection = mDeviceConnection;
      USBDevice.usbEpIn = usbEpIn;
      USBDevice.usbEpOut = usbEpOut;
    } else {
      mDeviceConnection.close();
    }
  }

  //文字提示方法
  private void showTmsg(String msg) {
    Log.d("xwr", msg);
    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
  }

  // 请求获取指定 USB 设备的权限
  public void requestPermission(UsbDevice device) {
    if (device != null) {
      if (manager.hasPermission(device)) {
        showTmsg("已经获取到权限");
      } else {
        if (mPermissionIntent == null) {
          IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
          mContext.registerReceiver(mUsbPermissionActionReceiver, filter);

          PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
          manager.requestPermission(device, mPermissionIntent);
        } else {
          showTmsg("请注册USB广播");
        }
      }
    }
  }

  private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (ACTION_USB_PERMISSION.equals(action)) {
        synchronized (this) {
          UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
          if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
            //user choose YES for your previously popup window asking for grant perssion for this usb device
            if (null != usbDevice) {
              afterGetUsbPermission();
            }
          } else {
            //user choose NO for your previously popup window asking for grant perssion for this usb device
            Toast.makeText(context, String.valueOf("Permission denied for device" + usbDevice), Toast.LENGTH_LONG).show();
          }
        }
      }
    }
  };


}
