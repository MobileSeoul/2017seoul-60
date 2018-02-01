package com.example.user.myoptionmenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopupresultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popupresult);
        Button buttonokay = (Button) findViewById(R.id.button1);
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        final String val = pref.getString("editText", null);
        SharedPreferences pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        final String val2 = pref2.getString("editText2", null);
        double num1 = Double.parseDouble(val);
        double num2 = Double.parseDouble(val2);
        double c = num2/60;
        double t = num1 * c;
        int alpha = (int)t;
        String t1 = String.valueOf(alpha);
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("결과 : " + t1 + "원");
        buttonokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadActivity threadActivity = (ThreadActivity) ThreadActivity.threadActivity;
                threadActivity.finish();
                Intent intent = new Intent (getApplicationContext(), Alarmactivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override public void onBackPressed() {

    }
}
