package li.itcc.lieventure.selfie;

import java.io.File;
import java.util.Vector;

import li.itcc.lieventure.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Uri> mImages = new Vector<Uri>();
    public ImageAdapter(Context c,File imagesFolder) {
        mContext = c;
        File[] directoryListing = imagesFolder.listFiles();
        if(directoryListing!=null) {
            for (File file : directoryListing) {
                //responsible for load at start, but causes outOfMemoryException
                //mImages.add(Uri.fromFile(file));
            }
        }
    }
    
    public void addImage(Uri image) {
        mImages.add(image);
    }
    
    public int getCount() {
        return mImages.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
  
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200,200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageURI(mImages.elementAt(position));
        int rotation = getCameraPhotoOrientation(mImages.elementAt(position));
        if(rotation != 0) {
            Matrix matrix=new Matrix();
            imageView.setScaleType(ScaleType.MATRIX);   //required
            matrix.postRotate(rotation, imageView.getDrawable().getBounds().width()/2, imageView.getDrawable().getBounds().height()/2);
            imageView.setImageMatrix(matrix);
        }
        return imageView;
    }
    
    private int getCameraPhotoOrientation(Uri imageUri){
        int rotate = 0;
        try {
            //context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imageUri.getPath());
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            }

            Log.v("orientation", "Exif orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return rotate;
    }

}
