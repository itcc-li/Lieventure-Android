
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;
import android.content.Context;
import android.content.res.Resources;

public class DistanceHintGenerator {
    private Resources fResources;
    private float fHotLimit;
    private float fWarmLimit;
    private float fColdLimit;
    private float fSteadyLimit;
    private DistanceHint[] fHintHistory;
    private int fHintHistoryIndex;
    private float[] fWalkHistory;
    private int fWalkHistoryIndex;

    public DistanceHintGenerator(Context context) {
        fResources = context.getResources();
        fHotLimit = limit(R.string.hotcold_limit_hot);
        fWarmLimit = limit(R.string.hotcold_limit_warm);
        fColdLimit = limit(R.string.hotcold_limit_cold);
        fSteadyLimit = limit(R.string.hotcold_limit_steady);
        fHintHistory = new DistanceHint[3];
        clearHintHistory();
        fWalkHistory = new float[4];
        clearWalkHistory();
    }

    private float limit(int id) {
        return Float.parseFloat(fResources.getString(id));
    }

    // make distance values be made as parameter
    public DistanceHint getDistanceHint(float oldDistance, float newDistance, float walkingDistance) {
        DistanceHint result;

        // calculates distance hint and returns distance Hint
        if (Math.abs(newDistance - oldDistance) < fSteadyLimit) {
            if (newDistance > fColdLimit) {
                result = DistanceHint.COLD;
            }
            else if (newDistance > fWarmLimit) {
                result = DistanceHint.WARM;
            }
            else if (newDistance > fHotLimit) {
                result = DistanceHint.HOT;
            }
            else {
                result = DistanceHint.AT_DESTINATION;
            }
        }
        else if (newDistance < oldDistance) {
            if (newDistance > fWarmLimit) {
                result = DistanceHint.WARMER;
            }
            else if (newDistance > fHotLimit) {
                result = DistanceHint.HOTTER;
            }
            else {
                result = DistanceHint.AT_DESTINATION;
            }
        }
        else {
            result = DistanceHint.COLDER;
        }
        fWalkHistory[fWalkHistoryIndex] = walkingDistance;
        fWalkHistoryIndex = (fWalkHistoryIndex + 1) % fWalkHistory.length;
        fHintHistory[fHintHistoryIndex] = result;
        fHintHistoryIndex = (fHintHistoryIndex + 1) % fHintHistory.length;
        if (isLongSteady()) {
            clearWalkHistory();
            return DistanceHint.STEADY;
            
        }
        if (isAll(DistanceHint.COLDER)) {
            clearHintHistory();
            return DistanceHint.TURN;
        }
        else if (isAll(DistanceHint.WARMER)) {
            clearHintHistory();
            return DistanceHint.WARMER_PROCEED;
        }
        else if (isAll(DistanceHint.HOTTER)) {
            clearHintHistory();
            return DistanceHint.HOTTER_PROCEED;
        }
        return result;
    }

    private boolean isLongSteady() {
        float sum = 0.0f;
        for (int i = 0; i < fWalkHistory.length; i++) {
            sum += fWalkHistory[i];
        }
        return sum < fSteadyLimit * fWalkHistory.length;
    }

    private void clearHintHistory() {
        for (int i = 0; i < fHintHistory.length; i++) {
            fHintHistory[i] = DistanceHint.COLD;
        }
    }

    private void clearWalkHistory() {
        for (int i = 0; i < fWalkHistory.length; i++) {
            fWalkHistory[i] = 100.0f;
        }
    }

    private boolean isAll(DistanceHint hint) {
        for (int i = 0; i < fHintHistory.length; i++) {
            if (fHintHistory[i] != hint) {
                return false;
            }
        }
        return true;
    }
}
