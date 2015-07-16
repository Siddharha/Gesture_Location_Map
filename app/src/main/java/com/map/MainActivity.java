package com.map;

import android.gesture.GestureOverlayView;
import android.gesture.GesturePoint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private GoogleMap map;
    GestureOverlayView gestureView;
    ArrayList<Integer> a_x,a_y;
    Integer i;
    ArrayList<GesturePoint> x;
    Projection projection;
    double Lat,Lng;
    Point x_y_points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        onGesture();
    }

    private void onGesture() {
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

                mapView.onPause();

            }

            @Override
            public void onGesture(GestureOverlayView overlay, MotionEvent event) {
                x = gestureView.getCurrentStroke();
                int o = i++;
                a_x.add(Math.round( x.get(o).x));
                a_y.add(Math.round(x.get(o).y));

                //////////////////////////////////////////////////////////////////////////////////////////


            }

            @Override
            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

                for(int q=0;q<a_x.size();q++)
                {
                    projection = map.getProjection();
                    x_y_points = new Point(a_x.get(q),a_y.get(q));
                    LatLng latLng = map.getProjection().fromScreenLocation(x_y_points);
                    Lat = latLng.latitude;
                    Lng = latLng.longitude;

                    Log.e("x = ",String.valueOf(Lat));
                    Log.e("y = ",String.valueOf(Lng));
                }

                //mapView.onResume();


                Toast.makeText(MainActivity.this,a_x.toString(), Toast.LENGTH_SHORT).show();

               // Log.e("x = ",a_x.toString());
               // Log.e("y = ",a_y.toString());
            }

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

            }
        });
    }

    private void initialize() {
        mapView = (MapView)findViewById(R.id.map);
        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        a_x = new ArrayList<Integer>();
        a_y = new ArrayList<Integer>();

        i = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeItem();
        createMap();
    }

    private void storeItem() {
        
    }


    private void createMap() {
        MapsInitializer.initialize(MainActivity.this);
        map = mapView.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.540735, 88.373858), 11));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}