package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;

public enum DistanceHint {
    COLD(R.raw.kaltmp3), WARM(R.raw.warmmp3), HOT(R.raw.heissmp3), ATDESTINATION(R.raw.zielerreichtmp3), WARMER(R.raw.waermermp3), HOTTER(R.raw.heissermp3), COLDER(R.raw.kaeltermp3);
    
    int mRessourceID;
    private DistanceHint(int distanceHint) {
        mRessourceID = distanceHint;
    }
    
    public int getRessourceID(){
        return mRessourceID; 
    }
    
    
}
