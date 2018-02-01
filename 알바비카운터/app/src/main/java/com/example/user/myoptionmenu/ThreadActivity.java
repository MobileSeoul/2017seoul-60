package com.example.user.myoptionmenu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.content;

public class ThreadActivity extends AppCompatActivity {
    public static Activity threadActivity;
    ActionBar abar;
    int value = 0;
    TextView textView;
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("진행중인 카운터를 종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("아니오", null)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_thread);
        abar = getSupportActionBar();
        abar.hide();
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        threadActivity = ThreadActivity.this;
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        final String val = pref.getString("editText", null);
        SharedPreferences pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        final String val2 = pref2.getString("editText2", null);
        textView = (TextView) findViewById(R.id.textView4);
        if (PopupActivity.color1 != null) {
            textView.setTextColor(Color.parseColor(PopupActivity.color1));
        }
        new Thread(new Runnable() {

            Handler handler2 = new Handler();
            double num1 = Double.parseDouble(val);
            double a = 3600000 / num1;
            long i = (long) a;
            double num2 = Double.parseDouble(val2);
            double c = num2/60;
            double t = num1 * c;
            boolean running = true;
            public void run() {
                while (running) {
                    if (value >= t) {
                        Intent intentw = new Intent(getApplicationContext(), PopupresultActivity.class);
                        startActivity(intentw);
                        break;
                    } else {
                        handler2.post(new Runnable() {
                            public void run() {
                                try {
                                    value += 1;
                                    textView.setText(java.text.NumberFormat.getInstance().format(value) + "원");
                                } catch (Exception e) {
                                }
                            }
                        });

                        try {
                            Thread.sleep(i);
                        } catch (Exception e) {
                        }

                    }
                }
            }
        }).start();

        }

    }

