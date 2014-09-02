
package li.itcc.lieventure.vaduztour.direction;

import android.content.Context;
import android.os.Handler;

public class DirectionLogic {
    private Context fContext;
    private DirectionCallback fDirectionCallback;
    private Handler fHandler;
    private Runnable fTickRunnable;
    private long fDelayMS = 50L;
    private boolean fRunning;
    private AngleSource fAngleSource;

    public DirectionLogic(Context context) {
        fContext = context;
        fHandler = new Handler();
        fTickRunnable = new Runnable() {

            @Override
            public void run() {
                onTick();
            }
            
        };
        fAngleSource = new RotateAngleSource();
    }

    protected void onTick() {
        if (!fRunning) {
            return;
        }
        fHandler.postDelayed(fTickRunnable, fDelayMS);
        float newAngle = fAngleSource.getAngle();
        fDirectionCallback.onAngleChange(newAngle);
    }

    public void onStart() {
        fRunning = true;
        fHandler.removeCallbacks(fTickRunnable);
        fHandler.post(fTickRunnable);
    }

    public void onStop() {
        fRunning = false;
        fHandler.removeCallbacks(fTickRunnable);
    }

    public void setDirectionCallback(DirectionCallback directionCallback) {
        fDirectionCallback = directionCallback;
    }
}
