package li.itcc.hackathon2014.vaduztour;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.vaduztour.hotcold.GPSLocationListener;
import li.itcc.hackathon2014.vaduztour.sculpture.GPSHandler;
import li.itcc.hackathon2014.vaduztour.sculpture.Tilt;

public class SculptureFragment extends AbstractTourFragment implements SensorEventListener, GPSLocationListener {
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private Button mBtnMeasurment;
    private TextView mTextView;
    private TextView mTextView_Tilt;
    private Tilt mCurrentTilt;
    private GPSHandler mGPSHandler;
    
    private final float METER_PER_DEZIMALGRAD_OW = 78714; // umrechnenung von gps daten in meter (ost west richtung)
    private final float METER_PER_DEZIMALGRAD_NS = 111319; // umrechnenung von gps daten in meter (nord süd richtung)
    private final float DELTA_ABSTAND_METER = 60; // meter
    private final float DELTA_TILT = 0.1f; // in grad
    
    // solution
    private final double SOLUTION_LOCATION_LATITUDE  = 47.149081; 
    private final double SOLUTION_LOCATION_LONGITUDE = 9.516950; 
    private final int SOLUTION_TILT = 30;
    
    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static SculptureFragment newInstance(int tourNumber, int tourPage) {
        SculptureFragment fragment = new SculptureFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    /**
     * constructor
     */
    public SculptureFragment() {
        mCurrentTilt = new Tilt();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sculpture, container, false);
        Button nextButton = (Button)rootView.findViewById(R.id.btnNext);
        setNextButton(nextButton);
        return rootView;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        // init ui elements
        mBtnMeasurment = (Button)getActivity().findViewById(R.id.btnMeasurement);
        mTextView = (TextView)getActivity().findViewById(R.id.textView2);
        mTextView_Tilt = (TextView)getActivity().findViewById(R.id.textView3);
        
        // init sensores
        mSensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY).get(0);
        mGPSHandler = new GPSHandler(getActivity());
                
        initListener();        
    }
       
    /**
     * check if user close to target
     * @return true|false
     */
    private boolean isNearbyByLocation(Location location) {        
        // calculate the different between ist/soll
        if (location != null) {
            double deltaX = SOLUTION_LOCATION_LONGITUDE - location.getLongitude();
            double deltaY = SOLUTION_LOCATION_LATITUDE - location.getLatitude();
            
            mTextView.setText("Lat: " + location.getLatitude() + "; Long: " + location.getLongitude());
            
            double diff = Math.sqrt(Math.pow((deltaX * METER_PER_DEZIMALGRAD_OW),2)  + Math.pow((deltaY * METER_PER_DEZIMALGRAD_NS),2));
            
            if (diff - DELTA_ABSTAND_METER <= 0.0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * check if z tilt correct
     * @return true|false
     */
    private boolean isTiltCorrect() {
        boolean isCorrect = false;
        if (Math.abs((SOLUTION_TILT - mCurrentTilt.getZ()) / 90) < DELTA_TILT) {
            isCorrect = true;
        }
        mTextView_Tilt.setText("Tilt: " + mCurrentTilt.getZ());
        
        return isCorrect;
    }
    
    /**
     * init listeners for services and ui elements
     */
    private void initListener() {
        mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        // TODO: change to gps
        //mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
               
        mBtnMeasurment.setOnClickListener(new OnClickListener() {
            @Override     
            public void onClick(View v) {
                mGPSHandler.startDelivery(SculptureFragment.this);
            }
        });
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                mCurrentTilt.setX(Math.round(Math.acos(event.values[0] / SensorManager.GRAVITY_EARTH) * 180 / Math.PI * 1000)/1000);
                mCurrentTilt.setY(Math.round(Math.acos(event.values[1] / SensorManager.GRAVITY_EARTH) * 180 / Math.PI * 1000)/1000);
                mCurrentTilt.setZ(Math.round(Math.acos(event.values[2] / SensorManager.GRAVITY_EARTH) * 180 / Math.PI * 1000)/1000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLocation(Location location) {
        boolean isNearbyCorrect = isNearbyByLocation(location);
        boolean isTiltCorrect = isTiltCorrect();
        
        if (isNearbyCorrect && isTiltCorrect) {
            Toast.makeText(getActivity(), "korrekt", Toast.LENGTH_LONG).show();
        } else {
            if (!isNearbyCorrect) {
                Toast.makeText(getActivity(), "Position nicht korrekt", Toast.LENGTH_LONG).show();
            }
            if (!isTiltCorrect) {
                Toast.makeText(getActivity(), "Neigung nicht korrekt", Toast.LENGTH_LONG).show();
            }   
        }
    }

    @Override
    public void onLocationSensorEnabled() {
    }

    @Override
    public void onLocationSensorDisabled() {
        Toast.makeText(getActivity(), "GPS nicht aktiviert", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
