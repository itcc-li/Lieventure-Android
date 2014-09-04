package li.itcc.lieventure.vaduztour.compass;

import li.itcc.lieventure.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CanvasView extends View implements SensorEventListener {
    Paint paint = new Paint();
    int fps;
    double rad;
    private Handler handler;
    float[] mGravity;
    float[] mGeomagnetic;
    private Context mContext;
    
    private GPSTracker tracker;
    private Location dest;
    
    private TextView distanceText;
    
    // GPS timer for battery usage
    private int timer;
    
    private double userRotationProtector;
    
    private Bitmap bg;
    private Bitmap needle;
    
    /*private double alt = 0;
    private double neu = 0;
    private double delta = 0;*/
    
    private double angleRad;
    
    private double angleForce;
    
    private double angleVelocity;
 
    private Runnable loop = new Runnable() { // update / draw interval
        public void run(){
            try {
                invalidate();
                
                // experimentell bestimmter aperiodischer grenzfall
                // angleForce -= angleVelocity * 8;
                
                // angleRad = neu;
                
                // neu -= angleVelocity * 8;
                /*neu -= angleVelocity * 0.02;
                
                angleVelocity += delta * 0.02;
 
                timer++;
                
                angleRad += angleVelocity;*/
                
                // experimentell bestimmter aperiodischer grenzfall
                
                angleVelocity += angleForce * 0.02;
                
                angleForce -= angleVelocity * 8;
                
                timer++;
                userRotationProtector++;
                
                if ((int)(angleForce / Math.PI) == 0) {
                    angleRad += angleVelocity;
                } else {
                    if (angleRad > Math.PI)
                        angleRad -= 2 * Math.PI;
                    else
                        angleRad += 2 * Math.PI;
                }
                
                // prepare and send the data here..
                handler.removeCallbacks(loop);
                handler.postDelayed(loop, 1000 / fps);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    public void setDistanceView(TextView view) {
        this.distanceText = view;
    }
    
    public CanvasView(Context ctx) {
        super(ctx);
        mContext = ctx;
        init();
    }
    
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    
    private void init() {
        fps = 20;
        rad = 0;
        handler = new Handler();
        
        tracker = new GPSTracker(mContext);
        // dest = tracker.createLocation(47.49557, 9.72601); //Bregenz
        // dest = tracker.createLocation(47.20379, 9.43950); // Gams
        // dest = tracker.createLocation(47.04731, 9.44649); // Mels
        // dest = tracker.createLocation(46.80984, 9.84531); // Davos
        // dest = tracker.createLocation(47.1491482, 9.5173952); // Current
        dest = tracker.createLocation(47.13965, 9.52208); // Dicke Frau
        
        timer = 0;
        
        userRotationProtector = 0;
        
        angleRad = 0;
        angleForce = 0;   
        angleVelocity = 0;

        /* imagery */
        Resources res = ((Activity)mContext).getResources();
        
        // distanceText = (TextView)getRootView().findViewById(R.id.distance_text);
        // String text = myTextView.getText().toString();
        
        // distanceText = (TextView)((View)this.getV.getParent().relativeLayout.getParent()).findViewBy  (TextView)(mContext.getResources().findViewById(R.id.distance_text));

        bg = BitmapFactory.decodeResource(res, R.drawable.compass_background);
        needle = BitmapFactory.decodeResource(res, R.drawable.compass_needle);
        
        SensorManager mSensorManager = (SensorManager)mContext.getApplicationContext().getSystemService(Activity.SENSOR_SERVICE);
        
        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        
        loop.run();
    }
    
    
    public void onSensorChanged(SensorEvent event) {
        float azimut;
        //double locationAngle;
        
        double lonDiff;
        double latDiff;
        
        if (userRotationProtector < fps / 2) return;
        
        userRotationProtector = 0;
        
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            /*Computes the inclination matrix I as well as the rotation matrix R transforming a vector from the device coordinate
            *  system to the world's coordinate system which is defined as a direct orthonormal basis*/
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                /*Computes the device's orientation based on the rotation matrix*/
                SensorManager.getOrientation(R, orientation);
                
                if (!this.tracker.isGPSEnabled) {
                    tracker.showSettingsAlert();
                }
                
                /* azimuth von Norden im Uhrzeigersinn */
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                
                // TODO: device orientation! (not ;))
                
                // System.out.println("azimut: " + azimut + " pitch: " + orientation[1] + " roll: " + orientation[2]);
                
                // check if GPS access is rejected
                if (timer > fps * 5) {
                    this.tracker = new GPSTracker(mContext);
                    
                    // reset timer
                    timer = 0;
                    
                    distanceText.setText("Distanz: " + (int)tracker.getDistance(dest) + "m");
                }
                
                lonDiff = (dest.getLongitude() - tracker.getLongitude()) * 75919;
                latDiff = (dest.getLatitude() - tracker.getLatitude()) * 111100;
                
                angleForce = 3 * Math.PI / 2 - (getRadFromPoint(lonDiff, latDiff) + azimut) - angleRad;
                //angleRad
                /*neu = 3 * Math.PI / 2 - (getRadFromPoint(lonDiff, latDiff) + azimut);

                if (neu - alt >= 0) {
                    if (neu - alt >= (2 * Math.PI)) delta = (neu - alt)- (2 * Math.PI);
                    else delta = (neu - alt);
                } else {
                    if (neu - alt < (-2 * Math.PI)) delta = (neu - alt)+ (2 * Math.PI);
                    else delta = (neu - alt);
                }
                
                // System.out.println("delta: " + delta);
                
                if (Math.abs(delta) >= 2 * Math.PI) {
                    System.out.println("break delta: " + delta);
                }
                
                neu = alt + delta;                               
                
                System.out.println("neu: " + neu);
                
                alt = neu;*/
            }
        }
        
        // mCustomDrawableView.invalidate();
    }
    
    /* Vier Quadranten Unterscheidung
     * Winkel mathematisch positiv! ab Norden
     * */
    private double getRadFromPoint(double x, double y) {
        double radius = Math.sqrt(x * x + y * y);
        if (x > 0)
            return Math.asin(y / radius) - Math.PI / 2;
        else
            return Math.PI / 2 -Math.asin(y / radius);
    }
    
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawNeedle(canvas, new Point(250, 250), 500);
    }
    
    @SuppressLint("DrawAllocation")
	private void drawBackground(Canvas canvas) {
        Rect source = new Rect(0, 0, 1000, 1000);
        Rect bitmapRect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(bg, source, bitmapRect, new Paint());
        
        /*Bitmap bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.compass_background.png);
        
        canvas.drawBitmap(bgImage, "", dst, paint);*/
    }
    
    private void drawNeedle(Canvas canvas, Point center, int radius) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        
        // bottom
        /*canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (int)(getWidth() / 2 + radius * Math.cos( angleRad )),
                (int)(getHeight() / 2 + radius * Math.sin( angleRad )), paint);*/
        
        canvas.rotate((float)(angleRad * (180 / Math.PI) + 90), canvas.getWidth() / 2, canvas.getHeight() / 2);
        
        /*canvas.setMatrix(matrix);
        canvas.rotate();*/
        
        Rect source = new Rect(0, 0, 1000, 1000);
        Rect bitmapRect = new Rect(getWidth() / 2 - 65, getHeight() / 2 - 390, getWidth() / 2 + 65, getHeight() / 2 + 74);
        canvas.drawBitmap(needle, source, bitmapRect, new Paint());
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }
}