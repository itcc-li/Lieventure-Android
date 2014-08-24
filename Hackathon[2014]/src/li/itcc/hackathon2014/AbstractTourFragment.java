
package li.itcc.hackathon2014;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class AbstractTourFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_TOUR_NUMBER = "tour_number";
    private static final String ARG_TOUR_PAGE = "tour_page";
    private Button fNextButton;
    private int fTourNumber;
    private int fTourPage;
    private boolean fAlwaysShowNextButton = false;

    public void setTourArguments(int tourNumber, int tourPage) {
        Bundle args = new Bundle();
        args.putInt(ARG_TOUR_NUMBER, tourNumber);
        args.putInt(ARG_TOUR_PAGE, tourPage);
        this.setArguments(args);
    }

    public AbstractTourFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        fTourNumber = args.getInt(ARG_TOUR_NUMBER);
        fTourPage = args.getInt(ARG_TOUR_PAGE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached(this, fTourNumber, fTourPage);
    }

    public void setNextButton(Button nextButton) {
        if (nextButton == null) {
            throw new NullPointerException();
        }
        fNextButton = nextButton;
        if (!fAlwaysShowNextButton) {
            fNextButton.setVisibility(View.GONE);
        }
        fNextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });
    }

    protected void onNextButtonClicked() {
        ((MainActivity) getActivity()).onFragmentNextClicked(this, fTourNumber, fTourPage);
    }

    protected void onTaskSolved() {
        fNextButton.setVisibility(View.VISIBLE);
    }

}
