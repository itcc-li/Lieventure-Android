
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;
import android.content.Context;
import android.content.res.Resources;

public class DistanceHintGenerator {
    private Resources fResources;
    private float fHotLimit;
    private float fWarmLimit;
    private float fColdLimit;
    private float fMinLimit;

    public DistanceHintGenerator(Context context) {
        fResources = context.getResources();
        fHotLimit = limit(R.string.hotcold_limit_hot);
        fWarmLimit = limit(R.string.hotcold_limit_warm);
        fColdLimit = limit(R.string.hotcold_limit_cold);
        fMinLimit = limit(R.string.hotcold_limit_minimum);
    }

    private float limit(int id) {
        return Float.parseFloat(fResources.getString(id));
    }

    // make distance values be made as parameter
    public DistanceHint getDistanceHint(float oldDistance, float newDistance, float walkingDistance) {
        // calculates distance hint and returns distance Hint
        if (Math.abs(newDistance - oldDistance) < fMinLimit) {
            if (newDistance > fColdLimit) {
                return DistanceHint.COLD;
            }
            else if (newDistance > fWarmLimit) {
                return DistanceHint.WARM;
            }
            else if (newDistance > fHotLimit) {
                return DistanceHint.HOT;
            }
            else {
                return DistanceHint.ATDESTINATION;
            }
        }
        else if (newDistance < oldDistance) {
            if (newDistance > fWarmLimit) {
                return DistanceHint.WARMER;
            }
            else if (newDistance > fHotLimit) {
                return DistanceHint.HOTTER;
            }
            else {
                return DistanceHint.ATDESTINATION;
            }
        } else {
            return DistanceHint.COLDER;
        }
    }
}
