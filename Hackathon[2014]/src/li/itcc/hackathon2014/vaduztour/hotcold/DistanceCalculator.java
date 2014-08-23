package li.itcc.hackathon2014.vaduztour.hotcold;

import android.location.Location;

public class DistanceCalculator {
    public static float getDistance(Location oldLocation, Location newLocation) {
        return oldLocation.distanceTo(newLocation);
    }
}
