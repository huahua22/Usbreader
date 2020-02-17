package com.xwr.usbreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xwr.usbreader.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  public void onIcc(View view) {
    startActivity(new Intent(MainActivity.this, UIccActivity.class));
  }

  public void onIdCard(View view) {
    startActivity(new Intent(MainActivity.this, UIdActivity.class));
  }
}
