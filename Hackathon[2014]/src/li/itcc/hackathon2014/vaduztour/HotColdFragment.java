
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.vaduztour.hotcold.GPSDeliverer;
import li.itcc.hackathon2014.vaduztour.hotcold.GPSLocationListener;
import android.app.Activity;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HotColdFragment extends AbstractTourFragment implements GPSLocationListener {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HotColdFragment newInstance(int tourNumber, int tourPage) {
        HotColdFragment fragment = new HotColdFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    private Button fStartStopButton;
    private Button fNextButton;
    private GPSDeliverer fDeliverer;
    private TextView fLocationLabel;
    private Location fTargetLocation;

    public HotColdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot_cold, container,
                false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();
        fTargetLocation = new Location("Config");
        fTargetLocation.setLatitude(Double.parseDouble(res.getString(R.string.hotcold_target_latitude)));
        fTargetLocation.setLongitude(Double.parseDouble(res.getString(R.string.hotcold_target_longitude)));
        fNextButton = (Button) view.findViewById(R.id.next_button);
        setNextButton(fNextButton);
        fStartStopButton = (Button) view.findViewById(R.id.hotcold_start_button);
        fStartStopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onStartStopClicked();
            }
        });
        fLocationLabel = (TextView) view.findViewById(R.id.hotcold_location_label);
        Activity a = getActivity();
        fDeliverer = new GPSDeliverer(a);
    }

    private void onStartStopClicked() {
        if (fDeliverer.isRunning()) {
            fStartStopButton.setText(R.string.hotcold_start_button);
            fDeliverer.stopDelivery();
            fLocationLabel.setText("");
        }
        else {
            fStartStopButton.setText(R.string.hotcold_stop_button);
            fDeliverer.startDelivery(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fDeliverer.isRunning()) {
            onStartStopClicked();
        }
    }

    @Override
    public void onLocation(Location location) {
        fLocationLabel.setText("Latitude: " + location.getLatitude() + " Longitude: "
                + location.getLongitude());
    }

    @Override
    public void onLocationSensorEnabled() {
        fLocationLabel.setText("Enabled");
    }

    @Override
    public void onLocationSensorDisabled() {
        fLocationLabel.setText("Disabled");
    }

}
