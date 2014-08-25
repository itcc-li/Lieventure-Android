package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.R;
import li.itcc.lieventure.AbstractTourFragment;
import li.itcc.lieventure.vaduztour.compass.CanvasView;
import li.itcc.lieventure.vaduztour.compass.GPSTracker;

import org.w3c.dom.Text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CompassFragment extends AbstractTourFragment implements OnClickListener {
    GPSTracker tracker;
    private CanvasView drawView;
    
    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static CompassFragment newInstance(int tourNumber, int tourPage) {
        CompassFragment fragment = new CompassFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }
    
    public CompassFragment() {
        // CanvasView canvas = new CanvasView();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_compass, container,
                false);
        Button nextButton = (Button)rootView.findViewById(R.id.next_button);
        
        setNextButton(nextButton);
        onTaskSolved();
        
        /*Button clickBtn = (Button)rootView.findViewById(R.id.test_button);
        clickBtn.setOnClickListener(this);
        
        Button distanceBtn = (Button)rootView.findViewById(R.id.distance_button);
        distanceBtn.setOnClickListener(this);*/

        drawView = (CanvasView)rootView.findViewById(R.id.compass_view);
        TextView distanceView = (TextView)rootView.findViewById(R.id.distance_text);
        
        drawView.setDistanceView(distanceView);
        
        return rootView;
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
        // tracker.getLocation().bearingTo(tracker.createLocation(47.1491993, 9.5174042));

//        SensorManager.getOrientation
//        float azimuth = // get azimuth from the orientation sensor (it's quite simple)
//        Location currentLoc = tracker.getLocation();
//        
//        azimuth = Math.toDegrees(azimuth);
//        
//        // convert radians to degrees
//        azimuth = Math.toDegrees(azimuth);
//        GeomagneticField geoField = new GeomagneticField(
//                         (float) currentLoc.getLatitude(),
//                         (float) currentLoc.getLongitude(),
//                         (float) currentLoc.getAltitude(),
//                         System.currentTimeMillis());
//        
//        azimuth += geoField.getDeclination(); // converts magnetic north into true north
//        float bearing = currentLoc.bearingTo(target); // (it's already in degrees)
//        float direction = azimuth - bearing;
        
        
        this.tracker = new GPSTracker(getActivity());
        
        int distance = (int)tracker.getDistance(
                         tracker.createLocation(47.1491993, 9.5174042));
        
        if (!this.tracker.isGPSEnabled) {
            tracker.showSettingsAlert();
        }
        
        switch (v.getId()) {
            /*case R.id.test_button:
                Toast.makeText(getActivity(), "current lat: " + Double.toString(tracker.getLatitude()) + " current long: " + Double.toString(tracker.getLongitude()), 150).show();
                break;
            case R.id.distance_button:
                // Toast.makeText(getActivity(), "bt: " + bt, 150).show();
                // Toast.makeText(getActivity(), "Current distance: " + distance + "m", 150).show();
                break;*/
        }
    }
}