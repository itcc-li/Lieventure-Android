package li.itcc.lieventure.vaduztour.sculpture;

import li.itcc.lieventure.utils.GPSLocationListener;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSHandler implements LocationListener {
    private Context mContext;
    private GPSLocationListener mListener;
    private LocationManager mLocationManager;
    private Location mLocation;
    private boolean isGPSEnabled;
    
    public GPSHandler(Context context) {
        mContext = context;
    }
    
    public void startDelivery(GPSLocationListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        mListener = listener;
        
        mLocationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        isGPSEnabled = true;
    }

    public void stopDelivery() {
        if (mListener != null) {
            mLocationManager.removeUpdates(this);
            mListener = null;
        }
    }
    
    @Override
    public void onLocationChanged(Location location) {
        if (mListener != null) {
            mListener.onLocation(location);
        }
        stopDelivery();        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }

    @Override
    public void onProviderEnabled(String provider) {
       isGPSEnabled = true;
       mListener.onLocationSensorEnabled();
    }

    @Override
    public void onProviderDisabled(String provider) { 
        isGPSEnabled = false;
        mListener.onLocationSensorDisabled();
    }
    
    public boolean getGPSEnabled() {
        return isGPSEnabled;
    }
    
}
