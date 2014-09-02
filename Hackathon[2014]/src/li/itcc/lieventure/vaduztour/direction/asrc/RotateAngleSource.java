
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.vaduztour.direction.AngleSource;

public class RotateAngleSource implements AngleSource {
    private float fVelocity = 3.0f;
    private float fAngle;

    @Override
    public boolean isAngleValid() {
        return true;
    }

    @Override
    public float getAngle(float deltaTime) {
        fAngle = fAngle + fVelocity * deltaTime;
        ;
        return fAngle;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

}
