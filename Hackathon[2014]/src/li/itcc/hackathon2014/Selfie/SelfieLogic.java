
package li.itcc.hackathon2014.Selfie;

import java.io.File;

import li.itcc.hackathon2014.MainActivity;
import li.itcc.hackathon2014.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

public class SelfieLogic {
    private static final String KEY_NEXT_SELFIE_PIC_NUM = "KEY_NEXT_SELFIE_PIC_NUM";
    private Activity fActivity;
    private int pictureNumber;
    private File fImagesFolder;

    public SelfieLogic(Activity a) {
        this.fActivity = a;
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        Resources res = fActivity.getResources();
        fImagesFolder = new File(picturesDirectory, res.getString(R.string.selfie_picture_path));
        fImagesFolder.mkdirs();
    }

    public void startTakePictureActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create Image Folder

        // Create Filename-String
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(fActivity);
        pictureNumber = settings.getInt(KEY_NEXT_SELFIE_PIC_NUM, 0);
        File output = getPictureFile(pictureNumber);
        // Prepare Save
        boolean skip = false;
        while (output.exists()) {
            pictureNumber++;
            skip = true;
            output = getPictureFile(pictureNumber);
        }
        if (skip) {
            Editor edit = settings.edit();
            edit.putInt(KEY_NEXT_SELFIE_PIC_NUM, pictureNumber);
            edit.commit();
        }
        Uri uriSavedImage = Uri.fromFile(output);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        fActivity.startActivityForResult(intent, MainActivity.REQUEST_CODE_TAKE_PICTURE);
    }

    private File getPictureFile(int pictureNumber) {
        String fileName = "Bild_" + String.valueOf(pictureNumber) + ".jpg";
        File output = new File(fImagesFolder, fileName);
        return output;
    }

    public void onPictureResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(fActivity);
        pictureNumber = settings.getInt(KEY_NEXT_SELFIE_PIC_NUM, 0);
        File input = getPictureFile(pictureNumber);
        if (!input.exists()) {
            return;
        }
       // BitmapFactory fb = BitmapFactory.decodeFile(input.getPath())
        

    }

    public void addWatermark(Bitmap bitmap1, int watermark) {
        // definieren .. pfad zum bitmap
        Bitmap bitmap2 = BitmapFactory.decodeResource(fActivity.getResources(), watermark);

        // create white Image
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(),
                Bitmap.Config.ARGB_8888);

        // create Canvas with white Image
        Canvas c = new Canvas(resultBitmap);

        // draw pic
        c.drawBitmap(bitmap1, 0, 0, null);
        Paint p = new Paint();
        p.setAlpha(127);

        // draw watermark
        c.drawBitmap(bitmap2, 0, 0, p);

        // try {
        // resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, null);
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // }

    }

}
