package li.itcc.lieventure.vaduztour.direction;

public interface AngleSource {
    
    void onResume();
    
    void onPause();
    
    float getAngle(float deltaTime);

}
