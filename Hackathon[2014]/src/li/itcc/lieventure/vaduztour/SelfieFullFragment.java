package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.R;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SelfieFullFragment extends Fragment{
    private Uri imageUri;
    public SelfieFullFragment()  {
    }
    
    public SelfieFullFragment(Uri uri) {
        imageUri = uri;     
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selfie_full, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_selfie_full);
        imageView.setImageURI(imageUri);
        return rootView;
    }

}
