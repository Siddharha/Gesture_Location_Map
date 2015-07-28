package com.map;

import android.gesture.GestureOverlayView;
import android.gesture.GesturePoint;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private GoogleMap map;
    GestureOverlayView gestureView;
    ArrayList<Integer> a_x,a_y;
    Integer i,q;
    ArrayList<GesturePoint> x;
    Projection projection;
    double Lat,Lng;
    Point x_y_points;
    FloatingActionButton fab;
    PolylineOptions options;
    LatLng latLng;
    Polyline polyline;
    ImageView imageView;
    Boolean xx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        onGesture();
        imageView.setVisibility(View.GONE);
        xx = false;
        Log.e("Gesture : ",String.valueOf(xx));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(xx)
                {
                    polyline.remove();
                    mapView.refreshDrawableState();
                    gestureView.clear(true);
                    options = new PolylineOptions().width(5).color(Color.RED).geodesic(false);
                    Log.e("points : ", options.getPoints().toString());
                }

                imageView.setVisibility(View.VISIBLE);
                mapView.onPause();
                gestureView.setEnabled(true);


            }
        });
    }

    public void btnG(View view)
    {

    }




    private void onGesture() {
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

                Log.e("Stroke : ","Started...");
                xx = true;
                Log.e("Gesture : ",String.valueOf(xx));
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

                for(q =0 ;q<a_x.size();q++)
                {
                    projection = map.getProjection();
                    x_y_points = new Point(a_x.get(q),a_y.get(q));
                    latLng = map.getProjection().fromScreenLocation(x_y_points);
                    Lat = latLng.latitude;
                    Lng = latLng.longitude;
                    options.add(latLng);
                    Log.e("x = ",String.valueOf(latLng));
                }

                gestureView.cancelGesture();
            }

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

                mapView.onResume();
                imageView.setVisibility(View.GONE);
                gestureView.setEnabled(false);
                gestureView.clear(true);
                options.add(options.getPoints().get(0));
                createMap();
                a_x.clear();
                a_y.clear();

                if(xx)
                {

                    polyline = map.addPolyline(options);
                }




                i = 0;
                q = 0;

            }
        });
    }




    private void initialize() {
        mapView = (MapView)findViewById(R.id.map);
        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        a_x = new ArrayList<>();
        a_y = new ArrayList<>();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        options = new PolylineOptions().width(5).color(Color.RED).geodesic(true);
        imageView = (ImageView)findViewById(R.id. imageView);
        i = 0; q =0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeItem();
        createMap();
        gestureView.setEnabled(false);

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
