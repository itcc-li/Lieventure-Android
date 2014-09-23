package li.itcc.lieventure.vaduztour.direction;

public interface DirectionCallback {
    
    void onAngleChange(float angle);
    
    void onAngleInvalid();
    
    void onStatusChange(String newStatus);
    
    void onTargetReached();


}
