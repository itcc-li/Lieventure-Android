
package li.itcc.lieventure.vaduztour.direction;

import li.itcc.lieventure.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DirectionView extends View {
    private Paint fPaint = new Paint();
    private Bitmap fCasing;
    private Bitmap fNeedle;
    private float fAngle;
    private Rect fCasingBounds;
    private Rect fTempRect = new Rect();
    private Rect fNeedleBounds;
    private Point fNeedlePivot;

    public DirectionView(Context ctx) {
        super(ctx);
        init();
    }

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DirectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public float getAngle() {
        return fAngle;
    }

    public void setAngle(float angle) {
        if (fAngle == angle) {
            return;
        }
        fAngle = angle;
        super.invalidate();
    }

    private void init() {
        fAngle = 0.0f;
        Resources res = getResources();
        fCasing = BitmapFactory.decodeResource(res, R.drawable.compass_background);
        fCasingBounds = new Rect(0, 0, fCasing.getWidth(), fCasing.getHeight());
        fNeedle = BitmapFactory.decodeResource(res, R.drawable.compass_needle);
        fNeedleBounds = new Rect(0, 0, fNeedle.getWidth(), fNeedle.getHeight());
        int halfWidth = fNeedle.getWidth() / 2;
        fNeedlePivot = new Point(halfWidth, fNeedle.getHeight() - halfWidth);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        drawBackground(canvas, width, height);
        drawNeedle(canvas, width, height);
    }

    private void drawBackground(Canvas canvas, int width, int height) {
        fTempRect.set(0, 0, width, height);
        canvas.drawBitmap(fCasing, fCasingBounds, fTempRect, fPaint);
    }

    private void drawNeedle(Canvas canvas, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        canvas.rotate(fAngle * 180.0f / (float)Math.PI, centerX, centerY);
        // scale and position the needle so that its pivot is at the center of the casing
        float needleScale = (float) centerY / (float) fNeedlePivot.y;
        int left = (int) (centerX - fNeedlePivot.x * needleScale);
        int right = left + (int) (fNeedleBounds.width() * needleScale);
        int top = 0;
        int bottom = top + (int) (fNeedleBounds.height() * needleScale);
        fTempRect.set(left, top, right, bottom);
        canvas.drawBitmap(fNeedle, fNeedleBounds, fTempRect, fPaint);
    }

}
