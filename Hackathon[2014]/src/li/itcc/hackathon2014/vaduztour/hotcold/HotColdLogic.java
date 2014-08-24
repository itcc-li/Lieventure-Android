
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.utils.GPSDeliverer;
import li.itcc.hackathon2014.utils.GPSLocationListener;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;

public class HotColdLogic implements GPSLocationListener {

    public interface HotColdLogicListener {
        void onHintText(String text);
        
        void onTargetReached();
    };

    private SpeechGenerator fSpeechGenerator;
    private GPSDeliverer fDeliverer;
    private HotColdLogicListener fListener;
    private Location fLastLocation;
    private Location fTargetLocation;
    private DistanceCalculator fDistanceCalculator;
    private DistanceHintGenerator fDistanceHintGenerator;
    private int locationCount;
    private boolean fDebugMode = false;
    private Resources fResources;

    public HotColdLogic(Context context) {
        fSpeechGenerator = new SpeechGenerator(context);
        fResources = context.getResources();
        fTargetLocation = new Location("Config");
        fTargetLocation.setLatitude(Double.parseDouble(fResources
                .getString(R.string.hotcold_target_latitude)));
        fTargetLocation.setLongitude(Double.parseDouble(fResources
                .getString(R.string.hotcold_target_longitude)));
        fDistanceCalculator = new DistanceCalculator();
        fDistanceHintGenerator = new DistanceHintGenerator(context);
        long speachDelay = Long.parseLong(fResources.getString(R.string.hotcold_speach_delay));
        fDeliverer = new GPSDeliverer(context, speachDelay);
    }

    public boolean isRunning() {
        return fDeliverer.isRunning();
    }

    public void stopDelivery() {
        fDeliverer.stopDelivery();
        fSpeechGenerator.stop();
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
        DistanceHint hint = fDistanceHintGenerator.getDistanceHint(oldDistance, newDistance,
                walkingDistance);
        if (hint == DistanceHint.AT_DESTINATION) {
            fListener.onTargetReached();
        }
        else {
            String hintText;
            if (fDebugMode) {
                hintText = "Latitude: " + location.getLatitude() + " Longitude: "
                        + location.getLongitude() + "\noldDistance: " + oldDistance + "\nnewDistance: "
                        + newDistance + "\nwalkingDistance: " + walkingDistance;
            }
            else {
                hintText = fResources.getString(hint.getTextHint());
            }
            fListener.onHintText(hintText);
        }
        fSpeechGenerator.say(hint.getAudioHint());
        fLastLocation = location;
    }

    @Override
    public void onLocationSensorEnabled() {
        fSpeechGenerator.say(R.raw.suech_satellit_mp3);
        fListener.onHintText(fResources.getString(R.string.hotcold_hint_search_sat));
    }

    @Override
    public void onLocationSensorDisabled() {
        if (locationCount > 2) {
            fSpeechGenerator.say(R.raw.hey_gps_ischalta_mp3);
            fListener.onHintText(fResources.getString(R.string.hotcold_hint_dont_turn_foo_gps));
        }
        else {
            fSpeechGenerator.say(R.raw.gps_ischalta_mp3);
            fListener.onHintText(fResources.getString(R.string.hotcold_hint_turn_on_gps));
        }
    }

    @Override
    public void onLocationSensorSearching() {
        fSpeechGenerator.say(R.raw.suech_satellit_mp3);
        fListener.onHintText(fResources.getString(R.string.hotcold_hint_search_sat));
    }

}
