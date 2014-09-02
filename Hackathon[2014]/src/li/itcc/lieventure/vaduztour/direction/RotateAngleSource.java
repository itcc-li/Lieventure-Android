package li.itcc.lieventure.vaduztour.direction;

public class RotateAngleSource implements AngleSource {
    private float fVelocity = 3.0f;
    private long fLastTime;
    private float fAngle;

    
    @Override
    public float getAngle() {
        long now = System.currentTimeMillis();
        long delta = now - fLastTime;
        fLastTime = now;
        if (delta <= 0L || delta > 10000L) {
            return fAngle;
        }
        float deltaTime = (float)delta / 1000L;
        float newAngle = fAngle + fVelocity * deltaTime;
        fAngle = newAngle;
        return fAngle;
    }

}
