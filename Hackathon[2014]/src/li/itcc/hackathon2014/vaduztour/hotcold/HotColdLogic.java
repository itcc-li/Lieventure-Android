
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;
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
        fDeliverer = new GPSDeliverer(context);
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
        fSpeechGenerator.say(R.raw.gpsischaltamp3);
        fListener.setLocationText("Enabled");
    }

    @Override
    public void onLocationSensorDisabled() {
        fSpeechGenerator.say(R.raw.heygpsischaltamp3);
        fListener.setLocationText("Disabled");
    }

}
