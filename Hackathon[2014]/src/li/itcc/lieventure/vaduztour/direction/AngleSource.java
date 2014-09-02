package li.itcc.lieventure.vaduztour.direction;

public interface AngleSource {
    
    void onResume();
    
    void onPause();
    
    boolean isAngleValid();
    
    float getAngle(float deltaTime);

}
