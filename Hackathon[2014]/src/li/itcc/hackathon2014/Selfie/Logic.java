package li.itcc.hackathon2014.Selfie;

import java.io.File;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;

public class Logic {
    
    private SelfieFragment activity;
    
    public Logic(SelfieFragment selfieFragment){
        activity = selfieFragment;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public String TakePictureIntent() {
        String _path = "data/data/li.itcc.hackathon2014.Selfie/";
        File file = new File( _path );
        Uri outputFileUri = Uri.fromFile( file );
            
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
            
        activity.startActivityForResult( intent, 0 );
    return _path;
    }
    
    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
            Camera.getCameraInfo( camIdx, cameraInfo );
            if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
                try {
                    cam = Camera.open( camIdx );
                } catch (RuntimeException e) {
                    //Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;}
}
