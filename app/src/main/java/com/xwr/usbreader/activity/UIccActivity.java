package com.xwr.usbreader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xwr.usbreader.R;
import com.xwr.usbreader.dev.usbapi.USBDevice;
import com.xwr.usbreader.dev.usbapi.UsbApi;
import com.xwr.usbreader.utils.UsbUtil;

import static com.xwr.usbreader.utils.HexUtil.bytesToHexString;

public class UIccActivity extends AppCompatActivity implements View.OnClickListener {
  Button mReaderInit;
  Button mPowerOn;
  Button mPowerOff;
  Button mReadWrite;
  TextView result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_uicc);
    mReaderInit = findViewById(R.id.readerInit);
    mPowerOn = findViewById(R.id.powerOn);
    mPowerOff = findViewById(R.id.powerOff);
    mReadWrite = findViewById(R.id.readWrite);
    result = findViewById(R.id.result);
    mReaderInit.setOnClickListener(this);
    mPowerOn.setOnClickListener(this);
    mReadWrite.setOnClickListener(this);
    mPowerOff.setOnClickListener(this);
    result.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  @Override
  public void onClick(View v) {
    long ret;
    byte slot = 0x01;
    switch (v.getId()) {
      case R.id.readerInit:
        if (USBDevice.mDeviceConnection != null) {
          ret = UsbApi.Reader_Init(USBDevice.mDeviceConnection, USBDevice.usbEpIn, USBDevice.usbEpOut);
          result.append("\nread init=" + ret);
        } else {
          showTmsg("请设置权限");
          try {
            UsbUtil.getInstance(this).initUsbData();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        break;
      case R.id.powerOn:
        byte[] atr = new byte[64];
        ret = UsbApi.ICC_Reader_PowerOn(slot, atr);
        result.append("\npower on=" + ret);
        if (ret > 0) {
          String data = null;
          data = bytesToHexString(atr, (int) ret);
          result.append(";data=" + data);
        } else {
          String data = new String("上电失败");
          result.append("\n" + data + " ret:" + ret);
        }
        ret = UsbApi.ICC_Reader_GetStatus(slot);
        result.append("\nstatus=" + ret);
        byte[] devId = new byte[64];
        ret = UsbApi.ICC_Reader_GetDevID(devId);
        result.append("\nget devId=" + ret + ";devId=" + bytesToHexString(devId, (int) ret));
        break;
      case R.id.readWrite:
        byte[] cmd = {0x00, (byte) 0x84, 0x00, 0x00, 0x08};
        byte[] apdu = new byte[64];
        ret = UsbApi.ICC_Reader_Application(slot, 5, cmd, apdu);
        result.append("\napplication=" + ret);
        if (ret > 0) {
          String data = null;
          data = bytesToHexString(apdu, (int) ret);
          result.append(";data=" + data);
        } else {
          String data = new String("取随机数失败");
          result.append("\n" + data);
        }
        break;
      case R.id.powerOff:
        ret = UsbApi.ICC_Reader_PowerOff(slot);
        result.append("\npowerOff=" + ret);
        result.append("\n------------------------");
        break;
    }
  }

  //文字提示方法
  private void showTmsg(String msg) {
    Toast.makeText(UIccActivity.this, msg, Toast.LENGTH_SHORT).show();
  }



}
