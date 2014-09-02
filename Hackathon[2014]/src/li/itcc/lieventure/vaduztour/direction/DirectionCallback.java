package li.itcc.lieventure.vaduztour.direction;

public interface DirectionCallback {
    
    void onAngleChange(float angle);
    
    void onStatusChange(String newStatus);

}
