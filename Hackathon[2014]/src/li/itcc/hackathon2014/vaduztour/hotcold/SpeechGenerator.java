
package li.itcc.hackathon2014.vaduztour.hotcold;

import android.content.Context;
import android.media.MediaPlayer;

public class SpeechGenerator {

    private Context fContext;

    public SpeechGenerator(Context context) {
        fContext = context;
    }

    public void say(int resourceId) {
        MediaPlayer mPlayer = MediaPlayer.create(fContext, resourceId);
        mPlayer.start();
    }
}
