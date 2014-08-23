package li.itcc.hackathon2014.vaduztour.hotcold;

public class DistanceHintGenerator {
    //make distance values be made as parameter
    public DistanceHint getDistanceHint(float oldDistance, float newDistance, float walkingDistance) {
        //calculates distance hint and returns distance Hint
        if(newDistance < oldDistance) {
            if(newDistance > 20) return DistanceHint.WARMER;
            else if(newDistance > 8) return DistanceHint.HOTTER;
            else return DistanceHint.ATDESTINATION;
        } else if (newDistance == oldDistance){
            if(newDistance > 50) return DistanceHint.COLD;
            else if(newDistance > 20) return DistanceHint.WARM;
            else if(newDistance > 8) return DistanceHint.HOT;
            else return DistanceHint.ATDESTINATION;
        }else {
            return DistanceHint.COLDER;
        }
    }
}
