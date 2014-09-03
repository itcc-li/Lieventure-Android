
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.R;
import li.itcc.lieventure.utils.GPSDeliverer;
import li.itcc.lieventure.utils.GPSLocationListener;
import li.itcc.lieventure.vaduztour.direction.AngleSource;
import android.content.Context;
import android.location.Location;

public class DirectionAngleSource implements AngleSource, GPSLocationListener {
    private Context fContext;
    private AzimuthAngleSource fAzimuthAngleSource;
    private Location fTargetLocation;
    private Location fCurrentLocation;
    private GPSDeliverer fGpsDeliverer;
    private String fStatusText;

    public DirectionAngleSource(Context context) {
        fContext = context;
        fAzimuthAngleSource = new AzimuthAngleSource(fContext);
        fGpsDeliverer = new GPSDeliverer(context, 2000L);
    }
    
    public void setTargetLocation(Location location) {
        fTargetLocation = location;
    }

    @Override
    public boolean isAngleValid() {
       return fTargetLocation != null && fCurrentLocation != null && fAzimuthAngleSource.isAngleValid();
    }

    @Override
    public float getAngle(float deltaTime) {
        float azimuth = fAzimuthAngleSource.getAngle(deltaTime);
        if (fTargetLocation == null || fCurrentLocation == null) {
            return azimuth;
        }
        float bearing = fCurrentLocation.bearingTo(fTargetLocation);
        float bearingRad = bearing * (float)Math.PI / 180.0f;
        return azimuth + bearingRad;
    }
 
    @Override
    public void onResume() {
        fCurrentLocation = null;
        fAzimuthAngleSource.onResume();
        fGpsDeliverer.startDelivery(this);
    }

    @Override
    public void onPause() {
        fCurrentLocation = null;
        fAzimuthAngleSource.onPause();
        fGpsDeliverer.stopDelivery();
    }

    @Override
    public void onLocation(Location location) {
        fCurrentLocation = location;
        fStatusText = "";
    }

    @Override
    public void onLocationSensorSearching() {
        fCurrentLocation = null;
        fStatusText = fContext.getResources().getString(R.string.direction_search_GPS);
    }

    @Override
    public void onLocationSensorEnabled() {
        fStatusText = fContext.getResources().getString(R.string.direction_search_GPS);
    }

    @Override
    public void onLocationSensorDisabled() {
        fCurrentLocation = null;
        fStatusText = fContext.getResources().getString(R.string.direction_enable_GPS);
    }

    public String getStatus() {
        int distance = getDistance();
        if (distance >= 0) {
            return Integer.toString(distance) + fContext.getResources().getString(R.string.direction_meter);
        }
        return fStatusText;
    }

    public int getDistance() {
        if (fTargetLocation == null || fCurrentLocation == null) {
            return -1;
        }
        int distance = (int)fCurrentLocation.distanceTo(fTargetLocation);
        return distance;
    }
}
