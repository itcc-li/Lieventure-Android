
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IntroFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static IntroFragment newInstance(int tourNumber, int tourPage) {
        IntroFragment fragment = new IntroFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public IntroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro, container,
                false);
        Button nextButton = (Button)rootView.findViewById(R.id.intro_start_button);
        setNextButton(nextButton);
        return rootView;
    }

}
