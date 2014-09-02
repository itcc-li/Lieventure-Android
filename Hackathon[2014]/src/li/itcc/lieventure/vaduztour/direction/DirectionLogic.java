
package li.itcc.lieventure.vaduztour.direction;

import li.itcc.lieventure.R;
import li.itcc.lieventure.vaduztour.direction.asrc.DirectionAngleSource;
import li.itcc.lieventure.vaduztour.direction.asrc.RotationalMassAngleSource;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Handler;

public class DirectionLogic {
    private Context fContext;
    private DirectionCallback fDirectionCallback;
    private Handler fHandler;
    private Runnable fTickRunnable;
    private long fDelayMS = 50L;
    private long fLastTickTime = 0;
    private boolean fRunning;
    private AngleSource fAngleSource;
    private DirectionAngleSource fDirectionAngleSource;
    private int tickCount;

    public DirectionLogic(Context context) {
        fContext = context;
        fHandler = new Handler();
        fTickRunnable = new Runnable() {

            @Override
            public void run() {
                onTick();
            }

        };
        // fAngleSource = new RotateAngleSource();
        // fAngleSource = new ConstantAngleSource();
        // fAngleSource = new RotationalMassAngleSource(fConstantAngleSource);
        // fAngleSource = new OrientationAngleSource(fContext);
        // fAngleSource = new AzimuthAngleSource(fContext);
        // fAngleSource = new DirectionAngleSource(fContext);
        // fAngleSource = new RotationalMassAngleSource(new
        // AzimuthAngleSource(fContext));
        fDirectionAngleSource = new DirectionAngleSource(fContext);
        fAngleSource = new RotationalMassAngleSource(fDirectionAngleSource);
        Resources resources = context.getResources();
        Location targetLocation = new Location("Target");
        targetLocation.setLatitude(Double.parseDouble(resources
                .getString(R.string.direction_target_latitude)));
        targetLocation.setLongitude(Double.parseDouble(resources
                .getString(R.string.direction_target_longitude)));
        fDirectionAngleSource.setTargetLocation(targetLocation);
    }

    protected void onTick() {
        if (!fRunning) {
            return;
        }
        fHandler.postDelayed(fTickRunnable, fDelayMS);
        long now = System.currentTimeMillis();
        if (fLastTickTime == 0L) {
            fLastTickTime = now;
        }
        long deltaTimeMS = now - fLastTickTime;
        fLastTickTime = now;
        float deltaTime = (float) deltaTimeMS / 1000L;
        boolean valid = fAngleSource.isAngleValid();
        if (valid) {
            float newAngle = fAngleSource.getAngle(deltaTime);
            fDirectionCallback.onAngleChange(newAngle);
        }
        else {
            fDirectionCallback.onAngleInvalid();
        }
        // text
        tickCount++;
        if (tickCount >= 1000 / fDelayMS) {
            tickCount = 0;
            String distance = fDirectionAngleSource.getStatus();
            fDirectionCallback.onStatusChange(distance);
        }
    }

    public void onResume() {
        fAngleSource.onResume();
        fRunning = true;
        fLastTickTime = 0L;
        fHandler.removeCallbacks(fTickRunnable);
        fHandler.post(fTickRunnable);
    }

    public void onPause() {
        fAngleSource.onPause();
        fRunning = false;
        fHandler.removeCallbacks(fTickRunnable);
    }

    public void setDirectionCallback(DirectionCallback directionCallback) {
        fDirectionCallback = directionCallback;
    }

}
