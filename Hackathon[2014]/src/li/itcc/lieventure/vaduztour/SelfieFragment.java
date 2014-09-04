package li.itcc.lieventure.vaduztour;


import li.itcc.lieventure.R;
import li.itcc.lieventure.Selfie.SelfieLogic;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        fSelfieLogic= new SelfieLogic(getActivity());
        Button start_button = (Button) rootView.findViewById(R.id.selfie_start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                fSelfieLogic.startTakePictureActivity();
                
            }
        });
        return rootView;
    }

}
