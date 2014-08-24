
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import li.itcc.hackathon2014.vaduztour.hotcold.HotColdLogic;
import li.itcc.hackathon2014.vaduztour.hotcold.HotColdLogic.HotColdLogicListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HotColdFragment extends AbstractTourFragment implements HotColdLogicListener {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HotColdFragment newInstance(int tourNumber, int tourPage) {
        HotColdFragment fragment = new HotColdFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    private Button fStartStopButton;
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
        fStartStopButton = (Button) view.findViewById(R.id.hotcold_start_button);
        fStartStopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onStartStopClicked();
            }
        });
        fHintLabel = (TextView) view.findViewById(R.id.hotcold_hint_label);
        Activity a = getActivity();
        fLogic = new HotColdLogic(a);
    }

    private void onStartStopClicked() {
        if (fLogic.isRunning()) {
            fStartStopButton.setText(R.string.hotcold_start_button);
            fLogic.stopDelivery();
            fHintLabel.setText(R.string.hotcold_hint_text_default);
        }
        else {
            fStartStopButton.setText(R.string.hotcold_stop_button);
            fLogic.startDelivery(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fLogic.isRunning()) {
            onStartStopClicked();
        }
    }

    @Override
    public void onHintText(String text) {
        fHintLabel.setText(text);
    }

    @Override
    public void onTargetReached() {
        if (fLogic.isRunning()) {
            fStartStopButton.setText(R.string.hotcold_start_button);
            fLogic.stopDelivery();
            fHintLabel.setText(R.string.hotcold_hint_text_finish);
        }
        onTaskSolved();
    }

}
