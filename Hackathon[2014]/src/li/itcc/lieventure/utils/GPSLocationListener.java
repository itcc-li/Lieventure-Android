
package li.itcc.lieventure.utils;

import android.location.Location;

public interface GPSLocationListener {

    public void onLocation(Location location);
    
    public void onLocationSensorSearching();

    public void onLocationSensorEnabled();

    public void onLocationSensorDisabled();

}
