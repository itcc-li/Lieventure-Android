
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;

public enum DistanceHint {
    COLD(R.raw.kalt_mp3),
    WARM(R.raw.warm_mp3),
    WARMER(R.raw.waermer_mp3),
    WARMER_PROCEED(R.raw.waermer_wiiter_so_mp3),
    HOT(R.raw.heiss_mp3),
    HOTTER(R.raw.heisser_mp3),
    HOTTER_PROCEED(R.raw.heisser_mp3),
    ATDESTINATION(R.raw.ziel_erreicht_mp3),
    COLDER(R.raw.kaelter_mp3),
    TURN(R.raw.umtreia_mp3),
    STEADY(R.raw.laufa_mp3);

    int mRessourceID;

    private DistanceHint(int distanceHint) {
        mRessourceID = distanceHint;
    }

    public int getRessourceID() {
        return mRessourceID;
    }

}
