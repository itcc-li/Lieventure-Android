
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.vaduztour.direction.AngleSource;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AzimuthAngleSource implements AngleSource, SensorEventListener {
    private float fAngle;
    private Context fContext;
    private SensorManager fSensorManager;
    private Sensor fAccelerometer;
    private Sensor fMagnetometer;
    private float[] fGravity;
    private float[] fGeomagnetic;
    private boolean fIsValid;

    public AzimuthAngleSource(Context context) {
        fContext = context;
        fSensorManager = (SensorManager) fContext.getSystemService(Context.SENSOR_SERVICE);
        fAccelerometer = fSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fMagnetometer = fSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }
    
    @Override
    public boolean isAngleValid() {
        return fIsValid;
    }

    @Override
    public float getAngle(float deltaTime) {
        return fAngle;
    }

    @Override
    public void onResume() {
        fIsValid = false;
        fSensorManager.registerListener(this, fAccelerometer, SensorManager.SENSOR_DELAY_UI);
        fSensorManager.registerListener(this, fMagnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        fIsValid = false;
        fSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            fGravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            fGeomagnetic = event.values;
        }
        if (fGravity != null && fGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, fGravity, fGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                fAngle = -orientation[0]; // orientation contains: azimut, pitch
                                         // and roll
                fIsValid = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
