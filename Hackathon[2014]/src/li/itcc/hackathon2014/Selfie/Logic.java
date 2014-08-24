package li.itcc.hackathon2014.Selfie;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;

public class Logic {
    
    private SelfieFragment activity;
    
    public Logic(SelfieFragment selfieFragment){
        activity = selfieFragment;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public Uri TakePictureIntent() {
        String _path = "data/data/li.itcc.hackathon2014.Selfie/";
        File file = new File( _path );
        Uri outputFileUri = Uri.fromFile( file );
            
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
            
        activity.getActivity().startActivityForResult( intent, 0 );
    return outputFileUri;
    }
    
    private String saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(activity.getActivity().getApplicationContext());
         // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(mypath);

       // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
    
    public void addWatermark(Bitmap pic, Bitmap watermark) {
        // definieren .. pfad zum bitmap
        Bitmap bitmap1 = pic;
        Bitmap bitmap2 = watermark;
        
        //create white Image
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        
        //create Canvas with white Image
        Canvas c = new Canvas(resultBitmap);
        
        //draw pic
        c.drawBitmap(bitmap1, 0, 0, null);
        Paint p = new Paint();
        p.setAlpha(127);
        
        //draw watermark 
        c.drawBitmap(bitmap2, 0, 0, p);
        
    }  // TO DO: bmp 2 jpeg und speichern
}
