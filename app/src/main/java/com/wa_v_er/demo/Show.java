package com.wa_v_er.demo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Show extends AppCompatActivity {
    ConstraintLayout container;
    DatabaseHelper databasehelper;
    TextView DisplayTime;
    Button GetTime;
    SimpleDateFormat simpleDateFormat,simple;
    String time;
    Calendar calander;
    int i,datei;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        DisplayTime = (TextView)findViewById(R.id.textView1);
        GetTime = (Button)findViewById(R.id.button1);

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH");
        time = simpleDateFormat.format(calander.getTime());
        i= Integer.parseInt(time);

        simple = new SimpleDateFormat("EEEE");
        final String day =simple.format(new Date());
        Log.i("", "onCreate: "+day);

        i=i+2;
        Log.i("print", "onCreate: "+i);
        GetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DisplayTime.setText(day+"");


            }
        });

    }

}
