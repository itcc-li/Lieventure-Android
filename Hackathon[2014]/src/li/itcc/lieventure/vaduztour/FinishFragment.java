
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.AbstractTourFragment;
import li.itcc.lieventure.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FinishFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static FinishFragment newInstance(int tourNumber, int tourPage) {
        FinishFragment fragment = new FinishFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public FinishFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_finish, container,
                false);
        return rootView;
    }

}
