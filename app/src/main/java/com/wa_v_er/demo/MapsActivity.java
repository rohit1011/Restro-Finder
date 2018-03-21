package com.wa_v_er.demo;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int currenttime, day;
    double f, r;
    TextView DisplayTime;
    SimpleDateFormat simpleDateFormat;
    String time;
    Button GetTime;
    Calendar calander;
    DatabaseHelper databaseHelper;
    String fetchurl = "http://192.168.1.100/map/select.php";
    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        databaseHelper = new DatabaseHelper(this);
        aQuery = new AQuery(this);
        fetchurl();//fetch from server and save it to local database
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DisplayTime = (TextView) findViewById(R.id.textView1);
        GetTime = (Button) findViewById(R.id.button1);
//code to fetch the current time.
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH");
        time = simpleDateFormat.format(calander.getTime());
        currenttime = Integer.parseInt(time);
        Log.i("print", "onCreate: " + day);
        // DisplayTime.setText(currenttime+"");

        GetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                fetchurl();
                Toast.makeText(MapsActivity.this, "rohit", Toast.LENGTH_LONG).show();
            }
        });

        ArrayList<Restinfo> list = databaseHelper.getrestList();
        for (int i = 0; i < list.size(); i++) {
            final Restinfo info = list.get(i);
            Log.i("customername" + info.name, "on buy: " + info.alls);
            Log.i("name", "" + info.name);
            Log.i("time", "" + info.otime);
            if (info.alls == 0) {
                LatLng sydney = new LatLng(info.lat, info.longi);
                mMap.addMarker(new MarkerOptions().position(sydney)
                        .title("name " + info.name + "opentime " + info.otime + "closetime " + info.ctime)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            } else if (info.alls == 1) {

                // Add a marker in Sydney and move the camera
                if (currenttime > info.otime && currenttime < info.ctime) {
                    LatLng sydney = new LatLng(info.lat, info.longi);
                    mMap.addMarker(new MarkerOptions().position(sydney)
                            .title("name " + info.name + "opentime " + info.otime + "closetime " + info.ctime)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    Log.i("time", "" + info.otime);

                } else if (currenttime < info.otime) {
                    LatLng sydney = new LatLng(info.lat, info.longi);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("name " + info.name + "opentime\t" + info.otime + "closetime " + info.ctime).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                } else if (currenttime > info.ctime && currenttime > info.otime) {
                    LatLng sydney = new LatLng(info.lat, info.longi);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("name " + info.name + "opentime " + info.otime + "closetime " + info.ctime).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.isMyLocationEnabled();
       }


    public void fetchurl() {
        databaseHelper.delete();
        aQuery.ajax(fetchurl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("rohitt", url + "callback: " + array);
                Log.i("rohitt", url + "callback: ");
                ArrayList<Restinfo> list = new ArrayList<Restinfo>();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        Restinfo info = new Restinfo();
                        info.id = object.getString("id");
                        info.name = object.getString("name");
                        info.address = object.getString("address");
                        info.otime = Integer.parseInt(object.getString("otime"));
                        info.ctime = Integer.parseInt(object.getString("ctime"));
                        info.longi = Float.parseFloat(object.getString("longi"));
                        info.lat = Float.parseFloat(object.getString("lat"));
                        info.alls = Integer.parseInt(object.getString("alls"));
                        list.add(info);

                        String nameValue = info.name;
                        String addressValue = info.address;
                        int otimeValue = Integer.parseInt(String.valueOf(info.otime));
                        int ctimeValue = Integer.parseInt(String.valueOf(info.ctime));
                        float longivalue = Float.parseFloat(String.valueOf(info.longi));
                        float latvalue = Float.parseFloat(String.valueOf(info.lat));
                        int allvalue = Integer.parseInt(String.valueOf(info.alls));


                        ContentValues contentValues = new ContentValues();
                        contentValues.put("name", nameValue);
                        contentValues.put("address", addressValue);
                        contentValues.put("otime", otimeValue);
                        contentValues.put("ctime", ctimeValue);
                        contentValues.put("long", longivalue);
                        contentValues.put("lat", latvalue);
                        contentValues.put("alls", allvalue);
                        databaseHelper.Restinfo(contentValues);
                        Log.i("everything oky", "callback: ");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                test();//load from database and show in map
            }
        });
    }

    public void test() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


}