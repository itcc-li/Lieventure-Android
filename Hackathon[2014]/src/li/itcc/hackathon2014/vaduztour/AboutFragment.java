
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static AboutFragment newInstance(int tourNumber, int tourPage) {
        AboutFragment fragment = new AboutFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container,
                false);
        return rootView;
    }

}
