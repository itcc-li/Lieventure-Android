
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.vaduztour.compass.CanvasView;
import li.itcc.hackathon2014.vaduztour.compass.GPSTracker;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
        
        Button clickBtn = (Button)rootView.findViewById(R.id.test_button);
        clickBtn.setOnClickListener(this);
        
        Button distanceBtn = (Button)rootView.findViewById(R.id.distance_button);
        distanceBtn.setOnClickListener(this);
        
        drawView = (CanvasView)rootView.findViewById(R.id.compass_view);
        
        return rootView;
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
        this.tracker = new GPSTracker(getActivity());
        
        int distance = (int)tracker.getDistance(
                         tracker.createLocation(47.1491993, 9.5174042));
        
        if (!this.tracker.isGPSEnabled) {
            tracker.showSettingsAlert();
        }
        
        switch (v.getId()) {
            case R.id.test_button:
                Toast.makeText(getActivity(), "current lat: " + Double.toString(tracker.getLatitude()) + " current long: " + Double.toString(tracker.getLongitude()), 150).show();
                break;
            case R.id.distance_button:
                Toast.makeText(getActivity(), "Current distance: " + distance + "m", 150).show();
                break;
        }
    }
}
