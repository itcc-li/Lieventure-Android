
package li.itcc.hackathon2014.vaduztour.hotcold;

import android.location.Location;

public interface GPSLocationListener {

    public void onLocation(Location location);

    public void onLocationSensorEnabled();

    public void onLocationSensorDisabled();

}
