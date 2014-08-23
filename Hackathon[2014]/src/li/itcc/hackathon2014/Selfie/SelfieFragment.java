
package li.itcc.hackathon2014.Selfie;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SelfieFragment extends AbstractTourFragment implements OnClickListener {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static SelfieFragment newInstance(int tourNumber, int tourPage) {
        SelfieFragment fragment = new SelfieFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public SelfieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selfie, container,
                false);
        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        setNextButton(nextButton);

        final Button testButton = (Button) rootView.findViewById(R.id.intro_button);
        testButton.setOnClickListener(this);
        return rootView;

    }

    public void Photo(View view) {
        String paths = null;
        Logic x = new Logic(this);
        paths = x.TakePictureIntent();
    }

    @Override
    public void onClick(View v) {
        Photo(v);
    }

    public void generate_bmp() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
    }

    // STAMP ITCC hinzufuegen
    public void stamp() {
        // definieren .. pfad zum bitmap
        Bitmap bitmap1 = null;
        Bitmap bitmap2 = null;
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(resultBitmap);
        c.drawBitmap(bitmap1, 0, 0, null);
        Paint p = new Paint();
        p.setAlpha(127);
        c.drawBitmap(bitmap2, 0, 0, p);
    } // Funktion ende
      // TO DO: bmp 2 jpeg und speichern

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawColor(Color.GRAY);
        // you need to insert some image flower_blue into res/drawable folder
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.Selfie);
        // Best of quality is 80 and more, 3 is very low quality of image
        Bitmap bJPGcompress = codec(b, Bitmap.CompressFormat.JPEG, 80);
        // get dimension of bitmap getHeight() getWidth()
        int h = b.getHeight();
        canvas.drawBitmap(b, 10, 10, paint); // <-- grösse es Bitmaps?
        canvas.drawBitmap(bJPGcompress, 10, 10 + h + 10, paint);
    }

}
