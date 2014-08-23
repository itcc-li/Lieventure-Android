package li.itcc.hackathon2014.Selfie;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.app.Activity;

public class Logic {
    
    private Context context; 
    
    public Logic(Context context){
        this.context=context;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        String _path;
        File file = new File( _path );
        Uri outputFileUri = Uri.fromFile( file );
            
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
            
        //startActivityForResult( intent, 0 );
    }
}
