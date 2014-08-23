package li.itcc.hackathon2014.vaduztour.hotcold;
import li.itcc.hackathon2014.R;
import android.content.Context;
import android.media.MediaPlayer;

public class SpeechGenerator {
    
    private Context fContext;
    public SpeechGenerator(Context context) {
        fContext = context; 
    }
    public void say(DistanceHint distanceHint) {
        MediaPlayer mPlayer = MediaPlayer.create(fContext,distanceHint.getRessourceID());
        mPlayer.start();
    }
}
