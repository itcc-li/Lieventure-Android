
package li.itcc.lieventure.vaduztour.direction;

import li.itcc.lieventure.vaduztour.direction.asrc.ConstantAngleSource;
import li.itcc.lieventure.vaduztour.direction.asrc.AzimuthAngleSource;
import li.itcc.lieventure.vaduztour.direction.asrc.RotationalMassAngleSource;
import android.content.Context;
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
    private ConstantAngleSource fConstantAngleSource;

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
        fConstantAngleSource = new ConstantAngleSource();
        //fAngleSource = new RotationalMassAngleSource(fConstantAngleSource);
        //fAngleSource = new OrientationAngleSource(fContext);
        fAngleSource = new RotationalMassAngleSource(new AzimuthAngleSource(fContext));
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
        float newAngle = fAngleSource.getAngle(deltaTime);
        fDirectionCallback.onAngleChange(newAngle);
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

    public void onTestClick() {
        fConstantAngleSource.setAngle(fConstantAngleSource.getAngle(0.0f) + 2f);
    }
}
