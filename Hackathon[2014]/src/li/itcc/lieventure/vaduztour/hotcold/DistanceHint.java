
package li.itcc.lieventure.vaduztour.hotcold;

import li.itcc.lieventure.R;

public enum DistanceHint {
    COLD(R.raw.hotcold_cold_mp3, R.string.hotcold_hint_cold),
    WARM(R.raw.hotcold_warm_mp3, R.string.hotcold_hint_warm),
    WARMER(R.raw.hotcold_warmer_mp3, R.string.hotcold_hint_warner),
    WARMER_PROCEED(R.raw.hotcold_warmer_proceed_mp3, R.string.hotcold_hint_warner_proceed),
    HOT(R.raw.hotcold_hot_mp3, R.string.hotcold_hint_hot),
    HOTTER(R.raw.hotcold_hotter_mp3, R.string.hotcold_hint_hotter),
    HOTTER_PROCEED(R.raw.hotcold_hotter_proceed_mp3, R.string.hotcold_hint_hotter_proceed),
    AT_DESTINATION(R.raw.hotcold_at_destination_mp3, R.string.hotcold_hint_at_destination),
    COLDER(R.raw.hotcold_colder_mp3, R.string.hotcold_hint_colder),
    TURN(R.raw.hotcold_turn_mp3, R.string.hotcold_hint_turn),
    STEADY(R.raw.hotcold_steady_mp3, R.string.hotcold_hint_steady);

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
