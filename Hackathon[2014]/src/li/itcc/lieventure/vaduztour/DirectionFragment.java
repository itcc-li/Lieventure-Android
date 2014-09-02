
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.AbstractTourFragment;
import li.itcc.lieventure.R;
import li.itcc.lieventure.vaduztour.direction.DirectionCallback;
import li.itcc.lieventure.vaduztour.direction.DirectionLogic;
import li.itcc.lieventure.vaduztour.direction.DirectionView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DirectionFragment extends AbstractTourFragment implements DirectionCallback {
    private DirectionLogic fLogic;
    private DirectionView fDirectionView;
    private TextView fStatusView;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static DirectionFragment newInstance(int tourNumber, int tourPage) {
        DirectionFragment fragment = new DirectionFragment();
        fragment.setTourArguments("direction", tourNumber, tourPage);
        return fragment;
    }

    public DirectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_direction, container,
                false);
        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        setNextButton(nextButton);
        onTaskSolved();
        fDirectionView = (DirectionView) rootView.findViewById(R.id.direction_view);
        fStatusView = (TextView) rootView.findViewById(R.id.direction_status_text);
        fLogic = new DirectionLogic(getActivity());
        fLogic.setDirectionCallback(this);
        return rootView;
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
    public void onAngleChange(float angle) {
        fDirectionView.setAngle(angle);
    }

    @Override
    public void onAngleInvalid() {
        fDirectionView.setAngleInvalid();
    }

    @Override
    public void onStatusChange(String newStatus) {
        fStatusView.setText(newStatus);
    }

}
