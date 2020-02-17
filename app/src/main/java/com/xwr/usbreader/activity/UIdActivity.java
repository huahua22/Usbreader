package com.xwr.usbreader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xwr.usbreader.R;
import com.xwr.usbreader.dev.usbapi.USBDevice;
import com.xwr.usbreader.dev.usbapi.UsbApi;
import com.xwr.usbreader.utils.HexUtil;
import com.xwr.usbreader.utils.UsbUtil;

public class UIdActivity extends AppCompatActivity implements View.OnClickListener {
  private TextView tvOutput, tvLeft1, tvLeft2;
  private ImageView mIvTest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_uid);
    ((Button) findViewById(R.id.btnleft01)).setOnClickListener(this);
    //    ((Button) findViewById(R.id.btnleft02)).setOnClickListener(this);
    //    mIvTest = (ImageView) findViewById(R.id.ivTest);
    //    tvLeft1 = (TextView) findViewById(R.id.tvLeft1);
    //    tvLeft2 = (TextView) findViewById(R.id.tvLeft2);
    tvOutput = (TextView) findViewById(R.id.tvOutput);
    tvOutput.setMovementMethod(ScrollingMovementMethod.getInstance());
  }


  @Override
  public void onClick(View v) {
    long ret;
    switch (v.getId()) {
      case R.id.btnleft01:
        if (USBDevice.mDeviceConnection != null) {
          ret = UsbApi.Reader_Init(USBDevice.mDeviceConnection, USBDevice.usbEpIn, USBDevice.usbEpOut);
          tvOutput.append("read init=" + ret);
          ret = UsbApi.Syn_Find_Card();
          tvOutput.append("\nfind card=" + ret);
          byte[] cardInfo = new byte[2048];
          ret = UsbApi.Syn_Read_Card(cardInfo);
          //          String data = new String(cardInfo);
          tvOutput.append("\nread card=" + ret + "; data=" + HexUtil.bytesToHexString(cardInfo, (int) ret));
//          ret = UsbApi.Syn_Get_Card(cardInfo);
//          tvOutput.append("\nget card=" + ret + "; data=" + HexUtil.bytesToHexString(cardInfo, (int) ret));
          tvOutput.append("\n------------------\n");
        } else {
          try {
            UsbUtil.getInstance(this).initUsbData();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
    }

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }


  public void tvAppend(String data) {
    tvOutput.append(data);
    int offset = tvOutput.getLineCount() * tvOutput.getLineHeight();
    if (offset > tvOutput.getHeight()) {
      tvOutput.scrollTo(0, offset - tvOutput.getHeight());
    }
  }
}
