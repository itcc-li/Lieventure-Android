
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.AbstractTourFragment;
import li.itcc.lieventure.R;
import li.itcc.lieventure.vaduztour.hotcold.HotColdLogic;
import li.itcc.lieventure.vaduztour.hotcold.HotColdLogic.HotColdLogicListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HotColdFragment extends AbstractTourFragment implements HotColdLogicListener {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HotColdFragment newInstance(int tourNumber, int tourPage) {
        HotColdFragment fragment = new HotColdFragment();
        fragment.setTourArguments("hotcold", tourNumber, tourPage);
        return fragment;
    }

    private Button fNextButton;
    private HotColdLogic fLogic;
    private TextView fHintLabel;

    public HotColdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot_cold, container,
                false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fNextButton = (Button) view.findViewById(R.id.next_button);
        setNextButton(fNextButton);
        fHintLabel = (TextView) view.findViewById(R.id.hotcold_hint_label);
        Activity a = getActivity();
        fLogic = new HotColdLogic(a);
        fLogic.setHotColdLogicListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        fLogic.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        fLogic.onPause();
    }

    @Override
    public void onHintText(String text) {
        fHintLabel.setText(text);
    }

    @Override
    public void onTargetReached() {
        if (fLogic.isRunning()) {
            fLogic.onPause();
            fHintLabel.setText(R.string.hotcold_hint_text_finish);
        }
        onTaskSolved();
    }

}
