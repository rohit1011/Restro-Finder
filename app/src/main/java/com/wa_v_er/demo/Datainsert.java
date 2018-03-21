package com.wa_v_er.demo;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Datainsert extends AppCompatActivity {
    EditText name,address,ctime,otime,longi,lat;
    Button submit;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainsert);
       name=findViewById(R.id.name);
       address =findViewById(R.id.address);
       ctime=findViewById(R.id.ctime);
       otime=findViewById(R.id.otime);
       longi=findViewById(R.id.longi);
       lat=findViewById(R.id.lat);
       databaseHelper=new DatabaseHelper(this);
       submit=findViewById(R.id.submit);
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String nameValue = name.getText().toString();
               String addressValue = address.getText().toString();
               int otimeValue = Integer.parseInt(otime.getText().toString());
               int ctimeValue = Integer.parseInt(ctime.getText().toString());
               float longivalue = Float.parseFloat(longi.getText().toString());
               float latvalue = Float.parseFloat(lat.getText().toString());


               ContentValues contentValues = new ContentValues();
               contentValues.put("name",nameValue);
               contentValues.put("address",addressValue);
               contentValues.put("otime",otimeValue);
               contentValues.put("ctime",ctimeValue);
               contentValues.put("long",longivalue);
               contentValues.put("lat",latvalue);
                databaseHelper.Restinfo(contentValues);
               Log.i("customername", "on buy: ");
               Intent intent = new Intent(Datainsert.this,MapsActivity.class);
               startActivity(intent);
           }
       });
    }
}
