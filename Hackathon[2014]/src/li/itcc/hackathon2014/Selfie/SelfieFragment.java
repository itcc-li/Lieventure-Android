
package li.itcc.hackathon2014.Selfie;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelfieFragment extends AbstractTourFragment {

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
        Button nextButton = (Button)rootView.findViewById(R.id.next_button);
        setNextButton(nextButton);
        return rootView;
    }
    
public void Photo(View view) {
//    String paths = null;
//    Logic x = new Logic(this);
//    paths = x.TakePictureIntent();
//    
}

}
