package com.example.user.myoptionmenu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;



import android.widget.Button;
import android.widget.Toast;

public class Alarmactivity extends AppCompatActivity implements OnDateChangedListener, OnTimeChangedListener {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    // 알람 메니저
    private AlarmManager mManager;
    // 설정 일시
    private GregorianCalendar mCalendar;
    //일자 설정 클래스
    SharedPreferences pref;
    private DatePicker mDate;
    SharedPreferences.Editor editor;
    //시작 설정 클래스
    SharedPreferences pref2;
    private TimePicker mTime;
    private long now;
    EditText edit1;
    SharedPreferences.Editor editor2;
    private NotificationManager mNotification;
    EditText edit2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //통지 매니저를 취득
        mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //알람 매니저를 취득
        mManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //현재 시각을 취득
        mCalendar = new GregorianCalendar();
        now = mCalendar.getTimeInMillis();
        //셋 버튼, 리셋버튼의 리스너를 등록
        setContentView(R.layout.activity_alarmactivity);


        Button b = (Button)findViewById(R.id.set);
        b.setOnClickListener (new View.OnClickListener() {
            public void onClick (View v) {
                pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
                editor2 = pref2.edit();
                edit2 = (EditText) findViewById(R.id.editText2);
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                edit1 = (EditText) findViewById(R.id.editText4);
                if (edit2.getText().toString().replace(" ", "").equals("")) {
                    Toast.makeText(getApplicationContext(), "시간을 설정해주세요.", Toast.LENGTH_LONG).show();
                } else if (edit2.getText().toString().equals("0") || edit1.getText().toString().equals("0")){
                   Toast.makeText(getApplicationContext(), "1 이상의 값을 설정하십시오.", Toast.LENGTH_LONG).show();
                } else if(edit1.getText().toString().replace(" ","").equals("")) {
                    Toast.makeText(getApplicationContext(), "시급을 설정해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    editor.putString("editText", edit1.getText().toString());
                    editor.commit();
                    editor2.putString("editText2", edit2.getText().toString());
                    editor2.commit();
                    setAlarm();
                }
            }
        });
        SharedPreferences pref2 = getSharedPreferences("시간저장용", Activity.MODE_PRIVATE);
        EditText edit2 = (EditText) findViewById(R.id.editText2);
        String text2 = pref2.getString("editText2", "");
        edit2.setText(text2);
        b = (Button)findViewById(R.id.reset);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "알람이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                resetAlarm();
            }
        });
        //일시 설정 클래스로 현재 시각을 설정
        mDate = (DatePicker)findViewById(R.id.date_picker);
        mDate.init (mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
        mTime = (TimePicker)findViewById(R.id.time_picker);
        mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTime.setOnTimeChangedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); //inflation시킴.
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();  //menu_main.xml에서 여기로 호출하는 방법
        switch(curId) {          //switch로 curId변수 얻을시 밑에것 함.
            case R.id.menu_refresh: //case:menu_refresh의 경우 클릭했을경우 밑에것 함. :임 ;아님
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "'뒤로'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }
    //알람의 설정
    private void setAlarm() {
        if (now > mCalendar.getTimeInMillis()){
            Toast.makeText(getApplicationContext(), "시간을 현재 또는 미래로 설정해주세요.", Toast.LENGTH_LONG).show();
            return;
        } else {
        mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent());
        Toast.makeText(getApplicationContext(), "곧 알람이 울립니다.", Toast.LENGTH_LONG).show();
    }}

    //알람의 해제
    private void resetAlarm() {
        mManager.cancel(pendingIntent());
    }
    //알람의 설정 시각에 발생하는 인텐트 작성
    private PendingIntent pendingIntent() {
        Intent i = new Intent(getApplicationContext(), ThreadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        return pi;
    }
    //일자 설정 클래스의 상태변화 리스너
    public void onDateChanged (DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set (year, monthOfYear, dayOfMonth, mTime.getCurrentHour(), mTime.getCurrentMinute());

    }
    //시각 설정 클래스의 상태변화 리스너
    public void onTimeChanged (TimePicker view, int hourOfDay, int minute) {
        mCalendar.set (mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), hourOfDay, minute);

    }
}
