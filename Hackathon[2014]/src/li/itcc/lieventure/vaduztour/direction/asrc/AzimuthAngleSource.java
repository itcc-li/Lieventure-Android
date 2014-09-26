
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.vaduztour.direction.AngleSource;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

public class AzimuthAngleSource implements AngleSource, SensorEventListener {
    private float fAngle;
    private Context fContext;
    private SensorManager fSensorManager;
    private Sensor fAccelerometer;
    private Sensor fMagnetometer;
    private float[] fGravity;
    private float[] fGeomagnetic;
    private boolean fIsValid;
    private int fRotation;
    private boolean fRemap = true;

    public AzimuthAngleSource(Context context) {
        fContext = context;
        fSensorManager = (SensorManager) fContext.getSystemService(Context.SENSOR_SERVICE);
        fAccelerometer = fSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fMagnetometer = fSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        String windoSrvc = Context.WINDOW_SERVICE;
        WindowManager wm = ((WindowManager)context.getSystemService(windoSrvc));
        Display display = wm.getDefaultDisplay();
        fRotation = display.getRotation();
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
            float inR[] = new float[9];
            float outR[];
            boolean success = SensorManager.getRotationMatrix(inR, null, fGravity, fGeomagnetic);
            if (success) {
                if (fRemap) {
                    // Remap the coordinates based on the natural device orientation.
                    int x_axis = SensorManager.AXIS_X; 
                    int y_axis = SensorManager.AXIS_Y;
    
                    switch (fRotation) {
                      case (Surface.ROTATION_90):  
                        x_axis = SensorManager.AXIS_Y; 
                        y_axis = SensorManager.AXIS_MINUS_X; 
                        break;
                      case (Surface.ROTATION_180): 
                        y_axis = SensorManager.AXIS_MINUS_Y; 
                        break;
                      case (Surface.ROTATION_270): 
                        x_axis = SensorManager.AXIS_MINUS_Y; 
                        y_axis = SensorManager.AXIS_X; 
                        break;
                      default: break;
                    }
                    outR = new float[9];
                    SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);    
                }
                else {
                    outR = inR;
                }
                float orientation[] = new float[3];
                SensorManager.getOrientation(outR, orientation);
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
