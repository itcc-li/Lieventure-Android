
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.vaduztour.direction.AngleSource;

public class ConstantAngleSource implements AngleSource {
    private float fAngle;

    @Override
    public float getAngle(float deltaTime) {
        return fAngle;
    }

    public void setAngle(float angle) {
        fAngle = angle;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

}
