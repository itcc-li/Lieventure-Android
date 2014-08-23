package li.itcc.hackathon2014.vaduztour.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    Paint paint = new Paint();
    int fps;
    double deg;
    private Handler handler;
    
    
    private Runnable loop = new Runnable() {
        public void run(){
            try {
                invalidate();
                
                deg++;
                
                //prepare and send the data here..
                handler.removeCallbacks(loop);
                handler.postDelayed(loop, 1000 / fps);    
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    public CanvasView(Context ctx) {
        super(ctx);
        init();
    }
    

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        fps = 20;
        deg = -90;
        handler = new Handler();
        
        loop.run();
        
    }

    
    
    
    public void onDraw(Canvas canvas) {
        System.out.println("repaint..");
        
        drawNeedle(canvas, new Point(600, 600), 500);
    }
    
    private void drawNeedle(Canvas canvas, Point center, int rad) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        
        canvas.drawLine((int)center.x, (int)center.y,
                (int)(center.x + rad * Math.cos( deg / 57 )),
                (int)(center.y + rad * Math.sin( deg / 57 )), paint);
        
        /*canvas.drawRect(30, 30, 120, 120, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );*/
    }
    
}
