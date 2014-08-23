
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.utils.GPSDeliverer;
import li.itcc.hackathon2014.utils.GPSLocationListener;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;

public class HotColdLogic implements GPSLocationListener {

    public interface HotColdLogicListener {
        void setLocationText(String text);
    };

    private SpeechGenerator fSpeechGenerator;
    private GPSDeliverer fDeliverer;
    private HotColdLogicListener fListener;
    private Location fLastLocation;
    private Location fTargetLocation;
    private DistanceCalculator fDistanceCalculator;
    private DistanceHintGenerator fDistanceHintGenerator;
    private int locationCount;

    public HotColdLogic(Context context) {
        fSpeechGenerator = new SpeechGenerator(context);
        Resources res = context.getResources();
        fTargetLocation = new Location("Config");
        fTargetLocation.setLatitude(Double.parseDouble(res
                .getString(R.string.hotcold_target_latitude)));
        fTargetLocation.setLongitude(Double.parseDouble(res
                .getString(R.string.hotcold_target_longitude)));
        fDistanceCalculator = new DistanceCalculator();
        fDistanceHintGenerator = new DistanceHintGenerator(context);
        long speachDelay = Long.parseLong(res.getString(R.string.hotcold_speach_delay));
        fDeliverer = new GPSDeliverer(context, speachDelay);
    }

    public boolean isRunning() {
        return fDeliverer.isRunning();
    }

    public void stopDelivery() {
        fDeliverer.stopDelivery();
    }

    public void startDelivery(HotColdLogicListener listener) {
        fListener = listener;
        fDeliverer.startDelivery(this);
    }

    @Override
    public void onLocation(Location location) {
        locationCount++;
        if (fLastLocation == null) {
            fLastLocation = location;
            return;
        }
        float oldDistance = fDistanceCalculator.getDistance(fLastLocation, fTargetLocation);
        float newDistance = fDistanceCalculator.getDistance(location, fTargetLocation);
        float walkingDistance = fDistanceCalculator.getDistance(fLastLocation, location);
        fListener.setLocationText("Latitude: " + location.getLatitude() + " Longitude: "
                + location.getLongitude() + "\noldDistance: " + oldDistance + "\nnewDistance: "
                + newDistance + "\nwalkingDistance: " + walkingDistance);
        DistanceHint hint = fDistanceHintGenerator.getDistanceHint(oldDistance, newDistance,
                walkingDistance);
        fSpeechGenerator.say(hint.getRessourceID());
        fLastLocation = location;
    }

    @Override
    public void onLocationSensorEnabled() {
        fSpeechGenerator.say(R.raw.suech_setellit_mp3);
        fListener.setLocationText("Searching...");
    }

    @Override
    public void onLocationSensorDisabled() {
        if (locationCount > 2) {
            fSpeechGenerator.say(R.raw.hey_gps_ischalta_mp3);
        }
        else {
            fSpeechGenerator.say(R.raw.gps_ischalta_mp3);
        }
        fListener.setLocationText("Disabled");
    }

}
