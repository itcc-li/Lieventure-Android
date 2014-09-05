package li.itcc.lieventure.vaduztour;


import li.itcc.lieventure.R;
import li.itcc.lieventure.selfie.ImageAdapter;
import li.itcc.lieventure.selfie.SelfieLogic;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

public class SelfieFragment extends Fragment {
    private SelfieLogic fSelfieLogic;
    
    public static SelfieFragment instanceOf() {
        return new SelfieFragment();
    }
    
    public SelfieFragment() {

    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selfie,container,false);
        // I don't think its correct to pass an instance of activity
        fSelfieLogic= new SelfieLogic(getActivity(), this);
        Button start_button = (Button) rootView.findViewById(R.id.selfie_start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fSelfieLogic.startTakePictureActivity();
                
            }
        });
        GridView gridview = (GridView) rootView.findViewById(R.id.selfie_gridview);
        gridview.setAdapter(fSelfieLogic.getImageAdapter());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SelfieLogic.REQUEST_CODE_TAKE_PICTURE) {
            fSelfieLogic.onPictureResult(requestCode, resultCode, data);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
