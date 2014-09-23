
package li.itcc.lieventure.vaduztour.direction.asrc;

import li.itcc.lieventure.vaduztour.direction.AngleSource;

public class RotationalMassAngleSource implements AngleSource {
    private float fVelocity;
    private float fAngle;
    private float fMass = 1.0f;
    private float fBrake = 0.8f;
    private AngleSource fForceDirection;

    public RotationalMassAngleSource(AngleSource forceDirection) {
        fForceDirection = forceDirection;
    }
    @Override
    public boolean isAngleValid() {
        return fForceDirection.isAngleValid();
    }

    @Override
    public float getAngle(float deltaTime) {
        float forceAngle = fForceDirection.getAngle(deltaTime);
        float torqueForce = (float)Math.sin(forceAngle - fAngle);
        float brakingForce = -fVelocity * fBrake;
        float acceleration = (torqueForce + brakingForce) / fMass;
        fVelocity = fVelocity + acceleration * deltaTime;
        fAngle = fAngle + fVelocity * deltaTime;
        return fAngle;
    }

    @Override
    public void onResume() {
        fForceDirection.onResume();
    }

    @Override
    public void onPause() {
        fForceDirection.onPause();
    }

}
