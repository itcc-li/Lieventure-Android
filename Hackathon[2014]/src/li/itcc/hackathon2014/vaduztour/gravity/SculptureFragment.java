package li.itcc.hackathon2014.vaduztour.gravity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;

public class SculptureFragment extends AbstractTourFragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private Button btnMeasurment;
    private Tilt mCurrentTilt;
    private LocationManager mLocationManager;
    private LocationListeners mLocationListener;
    
    private final float METER_PER_DEZIMALGRAD = 111319; // umrechnenung von gps daten in meter
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
        mLocationListener = new LocationListeners(); 
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
        btnMeasurment = (Button)getActivity().findViewById(R.id.btnMeasurement);
        
        // init sensores
        mSensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY).get(0);
        mLocationManager = (LocationManager)getActivity().getSystemService(getActivity().LOCATION_SERVICE);        
 
        // TODO: check is gps enabled
        initListener();
    }
   
    /**
     * check if all inputs correct
     */
    private void checkSculpture() {
        boolean isNearbyCorrect = isNearbyCorrect();
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
    
    /**
     * check if user close to target
     * @return true|false
     */
    private boolean isNearbyCorrect() {
        Location location = mLocationListener.getLocation();
        
        // calculate the different between ist/soll
        if (location != null) {
            double deltaX = SOLUTION_LOCATION_LONGITUDE - location.getLongitude();
            double deltaY = SOLUTION_LOCATION_LATITUDE - location.getLatitude();
            
            double diff = Math.sqrt(Math.pow((deltaX * METER_PER_DEZIMALGRAD),2)  + Math.pow((deltaY * METER_PER_DEZIMALGRAD),2));
            
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
        if (Math.abs((SOLUTION_TILT - mCurrentTilt.getZ()) / 90) < DELTA_TILT) {
            return true;
        }
        return false;
    }
    
    /**
     * init listeners for services and ui elements
     */
    private void initListener() {
        mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        // TODO: change to gps
        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
        
        btnMeasurment.setOnClickListener(new OnClickListener() {
            @Override     
            public void onClick(View v) {
                checkSculpture();
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
}
