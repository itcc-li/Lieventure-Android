
package li.itcc.hackathon2014.vaduztour.hotcold;

import li.itcc.hackathon2014.R;

public enum DistanceHint {
    COLD(R.raw.kalt_mp3, R.string.hotcold_hint_cold),
    WARM(R.raw.warm_mp3, R.string.hotcold_hint_warm),
    WARMER(R.raw.waermer_mp3, R.string.hotcold_hint_warner),
    WARMER_PROCEED(R.raw.waermer_wiiter_so_mp3, R.string.hotcold_hint_warner_proceed),
    HOT(R.raw.heiss_mp3, R.string.hotcold_hint_hot),
    HOTTER(R.raw.heisser_mp3, R.string.hotcold_hint_hotter),
    HOTTER_PROCEED(R.raw.heisser_wiiter_so_mp3, R.string.hotcold_hint_hotter_proceed),
    AT_DESTINATION(R.raw.ziel_erreicht_mp3, R.string.hotcold_hint_at_destination),
    COLDER(R.raw.kaelter_mp3, R.string.hotcold_hint_colder),
    TURN(R.raw.umtreia_mp3, R.string.hotcold_hint_turn),
    STEADY(R.raw.laufa_mp3, R.string.hotcold_hint_steady);

    private int mAudioHint;
    private int mTextHint;

    private DistanceHint(int distanceHint, int textHint) {
        mAudioHint = distanceHint;
        mTextHint = textHint;
    }

    public int getAudioHint() {
        return mAudioHint;
    }
    
    public int getTextHint() {
        return mTextHint;
    }

}
