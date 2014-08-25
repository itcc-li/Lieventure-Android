
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.R;
import li.itcc.lieventure.AbstractTourFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CastleFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static CastleFragment newInstance(int tourNumber, int tourPage) {
        CastleFragment fragment = new CastleFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public CastleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_castle, container,
                false);
        Button nextButton = (Button) rootView.findViewById(R.id.castle_start_button);
        setNextButton(nextButton);
        onTaskSolved();
        return rootView;
    }

}
