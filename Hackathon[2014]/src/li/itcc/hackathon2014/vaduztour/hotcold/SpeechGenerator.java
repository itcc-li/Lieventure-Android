
package li.itcc.hackathon2014.vaduztour.hotcold;

import android.content.Context;
import android.media.MediaPlayer;

public class SpeechGenerator {
    private Context fContext;
    private MediaPlayer fPlayer;

    public SpeechGenerator(Context context) {
        fContext = context;
    }

    public void say(int resourceId) {
        stop();
        fPlayer = MediaPlayer.create(fContext, resourceId);
        fPlayer.start();
    }
    
    public void stop() {
        if (fPlayer != null) {
            fPlayer.release();
            fPlayer = null;
        }
    }
}
