package li.itcc.hackathon2014.vaduztour.sculpture;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class LocationListeners implements LocationListener{        
    private Location mLocation;
    
    public void onLocationChanged(Location loc) {           
           mLocation = loc;
    }
    public void onProviderDisabled(String arg0) {
    }
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }       
    public Location getLocation() {
        return mLocation;
    }
}
