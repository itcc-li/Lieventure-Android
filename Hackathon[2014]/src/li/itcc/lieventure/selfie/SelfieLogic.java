
package li.itcc.lieventure.selfie;

import java.io.File;
import java.io.FileOutputStream;

import li.itcc.lieventure.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

public class SelfieLogic {
    private static final String KEY_NEXT_SELFIE_PIC_NUM = "KEY_NEXT_SELFIE_PIC_NUM";
    private Activity fActivity;
    private int pictureNumber;
    private File fImagesFolder;
    public static final int REQUEST_CODE_TAKE_PICTURE = 100;
    private ImageAdapter mImageAdapter;
    private Uri uriSavedImage;
    private Fragment fragment;

    public SelfieLogic(Activity a, Fragment f) {
        this.fActivity = a;
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Resources res = fActivity.getResources();
        fImagesFolder = new File(picturesDirectory, res.getString(R.string.selfie_picture_path));
        fImagesFolder.mkdirs();
       
        mImageAdapter = new ImageAdapter(a,fImagesFolder);
        fragment = f;
    }

    private File getPictureFile(int pictureNumber) {
        String fileName = "Bild_" + String.valueOf(pictureNumber) + ".jpg";
        File output = new File(fImagesFolder, fileName);
        return output;
    }
    
    public void startTakePictureActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        uriSavedImage = Uri.fromFile(output);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        fragment.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
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
        try { 
            Bitmap b = BitmapFactory.decodeFile(input.getPath());
            Bitmap result = addWatermark(b, R.raw.selfie_icon);
            FileOutputStream stream;
            stream = new FileOutputStream(input);
            result.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (Throwable th) {
            // silent catch
        }
        
        refresh();        
        mImageAdapter.addImage(uriSavedImage);
        mImageAdapter.notifyDataSetChanged();
    }

    private Bitmap addWatermark(Bitmap pictureBitmap, int watermark) {
        // definieren .. pfad zum bitmap
        Bitmap watermarkBitmap = BitmapFactory.decodeResource(fActivity.getResources(), watermark);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(pictureBitmap.getWidth(), pictureBitmap.getHeight(), conf);

        // create Canvas with white Image
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setAlpha(127);

        // draw watermark
        c.drawBitmap(watermarkBitmap, 0, 0, p);
        c.drawBitmap(pictureBitmap, 0, 0, p);
        return bmp;

    }
    
    private void refresh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File("file://"
                    + fImagesFolder);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            fActivity.sendBroadcast(mediaScanIntent);
        }
        else
        {
            fActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                    + fImagesFolder)));
        }
    }
    
    public ImageAdapter getImageAdapter() {
        return mImageAdapter;
    }
}
