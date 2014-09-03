
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.AbstractTourFragment;
import li.itcc.lieventure.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HotColdIntroFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HotColdIntroFragment newInstance(int tourNumber, int tourPage) {
        HotColdIntroFragment fragment = new HotColdIntroFragment();
        fragment.setTourArguments("hotcold_intro", tourNumber, tourPage);
        return fragment;
    }

    public HotColdIntroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot_cold_intro, container,
                false);
        Button nextButton = (Button)rootView.findViewById(R.id.button_next);
        setNextButton(nextButton);
        onTaskSolved();
        return rootView;
    }

}
