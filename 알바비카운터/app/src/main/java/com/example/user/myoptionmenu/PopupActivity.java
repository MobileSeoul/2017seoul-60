package com.example.user.myoptionmenu;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

public class PopupActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    static String color1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar action = getSupportActionBar();
        action.setTitle("색상 변경");
        setContentView(R.layout.activity_popup);

        RadioGroup rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rg1.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return true;
    }



    @Override
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        switch(arg1) {
            case R.id.radiogold:
                color1 = "#FFD700";
                break;
            case R.id.radiored:
                color1 = "#FF0000";
                break;
            case R.id.radioorange:
                color1 = "#FFA500";
                break;
            case R.id.radioyellow:
                color1 = "#FFFF00";
                break;
            case R.id.radiogreen:
                color1 = "#008000";
                break;
            case R.id.radioyellowgreen:
                color1 = "#ADFF2F";
                break;
            case R.id.radioblue:
                color1 = "#0000FF";
                break;
            case R.id.radionavy:
                color1 = "#000080";
                break;
            case R.id.radiopurple:
                color1 = "#800080";
                break;
            case R.id.radioblack:
                color1 = "#000000";
                break;
            case R.id.radiogray:
                color1 = "#808080";
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curid = item.getItemId();
        switch (curid) {
            case R.id.menu_okay:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
