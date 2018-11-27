package com.wa_v_er.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<Double> placelist1, placelist,latitude,longitude;
    private GoogleMap mMap;
    int currenttime, day;
    double f, r;
    TextView DisplayTime;
    SimpleDateFormat simpleDateFormat;
    String time;
    ImageButton Refresh;
    Button  getdirection;
    Calendar calander;
    DatabaseHelper databaseHelper;
    //String fetchurl = "http://192.168.1.104/Restromap/index.php/webservice/User/Restaurant";
    AQuery aQuery;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    GPSTracker gps;
    Polyline polyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        databaseHelper = new DatabaseHelper(this);
        aQuery = new AQuery(this);
        placelist1 = new ArrayList<Double>();
        test();
        gps = new GPSTracker(MapsActivity.this);
        placelist = new ArrayList<>();
        listPoints = new ArrayList<>();

        latitude = new ArrayList<>();
        longitude = new ArrayList<>();
        getdirection = findViewById(R.id.get_direction);

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

        double currentLatitude = gps.getLatitude();
        double currentLongitude = gps.getLongitude();
        mMap = googleMap;
        Refresh = findViewById(R.id.refresh);
//code to fetch the current time.
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH");
        time = simpleDateFormat.format(calander.getTime());
        currenttime = Integer.parseInt(time);
        Log.i("print", "onCreate: " + day);
        // DisplayTime.setText(currenttime+"");
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.isMyLocationEnabled();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 5000, null);
        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);



       Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mMap.clear();
                test();
                Toast.makeText(MapsActivity.this, "refreshing.....", Toast.LENGTH_LONG).show();
                gps = new GPSTracker(MapsActivity.this);
            }
        });


        String url = "https://restromap11.000webhostapp.com/index.php/webservice/User/Restaurant";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("Result"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        Restinfo place = new Restinfo();
                        place.id = obj.getString("id");
                        place.name = obj.getString("name");
                        place.address = obj.getString("address");
                        place.otime = Integer.parseInt(obj.getString("otime"));
                        place.ctime = Integer.parseInt(obj.getString("ctime"));
                        place.latitude = Double.parseDouble(obj.getString("lat"));
                        place.longitude = Double.parseDouble(obj.getString("longi"));
                        place.alls = Integer.parseInt(obj.getString("alls"));

                        placelist.add(place.latitude);
                        placelist1.add(place.longitude);


                        if (place.alls == 0) {
                            LatLng sydney = new LatLng(place.latitude, place.longitude);
                            mMap.addMarker(new MarkerOptions().position(sydney)
                                    .title("name " + place.name + "opentime " + place.otime + "closetime " + place.ctime)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        } else if (place.alls == 1) {

                            // Add a marker in Sydney and move the camera
                            if (currenttime > place.otime && currenttime < place.ctime) {
                                LatLng sydney = new LatLng(place.latitude, place.longitude);
                                mMap.addMarker(new MarkerOptions().position(sydney)
                                        .title("name " + place.name + "opentime " + place.otime + "closetime " + place.ctime)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                Log.i("time", "" + place.otime);

                            } else if (currenttime < place.otime) {
                                LatLng sydney = new LatLng(place.latitude, place.longitude);
                                mMap.addMarker(new MarkerOptions().position(sydney).title("name " + place.name + "opentime " + place.otime + " closetime " + place.ctime).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            } else if (currenttime > place.ctime && currenttime > place.otime) {
                                LatLng sydney = new LatLng(place.latitude, place.longitude);
                                mMap.addMarker(new MarkerOptions().position(sydney).title("name " + place.name + "opentime " + place.otime + " closetime " + place.ctime).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    // test();//load from database and show in map
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something went Wrong /n Check Your connection", Toast.LENGTH_LONG).show();

                    }
                });

        requestQueue.add(stringRequest);
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (polyline!=null){
                    polyline.remove();
                }
                latitude.clear();
                longitude.clear();
               final double lat = marker.getPosition().latitude;
                final double longi = marker.getPosition().longitude;
                latitude.add(lat);
                longitude.add(longi);
                getdirection.setVisibility(View.VISIBLE);
                Log.d("tag", "check latlong: " + latitude.get(0)+"   "+longitude.get(0));
                getdirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = getRequestUrl(lat,longi);
                        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                        taskRequestDirections.execute(url);
                        getdirection.setVisibility(View.GONE);
                    }
                });

                return false;
            }

        });

    }


    public void test() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private String getRequestUrl(Double latitudemarker,Double longitudemarker) {
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        //Value of origin
        String str_org = "origin=" + latitude + "," + longitude;
        //Value of destination
        String str_dest = "destination=" + latitudemarker+","+longitudemarker;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
        //Output format
        String output = "json";
        String key = "&key=" + "AIzaSyB-j0nf31jDb-8rxl9PaYNOoE7NsmF0PR8 ";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + key;
        Log.d("tag", "urlcheck" + url);
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {
        private ProgressDialog pdia;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(MapsActivity.this);
            pdia.setMessage("Finding Route...");
            pdia.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
            pdia.dismiss();
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;


            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(7);
                polylineOptions.color(Color.GREEN);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions!=null) {
               // mMap.addPolyline(polylineOptions);
                polyline = mMap.addPolyline(polylineOptions);
                           } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }

    }



}