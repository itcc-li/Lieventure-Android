
package li.itcc.lieventure;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class AbstractTourFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_TOUR_NUMBER = "tour_number";
    private static final String ARG_TOUR_PAGE = "tour_page";
    private static final String ARG_TOUR_FRAGMENT_ID = "tour_fragment_id";
    private static final String KEY_POSTFIX = ".isComplete";
    private Button fNextButton;
    private int fTourNumber;
    private int fTourPage;
    private boolean fAlwaysShowNextButton = false;
    private String fFragmentId;
    private String fPersistKey;
    private boolean fIsComplete;

    public void setTourArguments(String id, int tourNumber, int tourPage) {
        Bundle args = new Bundle();
        args.putString(ARG_TOUR_FRAGMENT_ID, id);
        args.putInt(ARG_TOUR_NUMBER, tourNumber);
        args.putInt(ARG_TOUR_PAGE, tourPage);
        this.setArguments(args);
    }

    public int getTourNumber() {
        return fTourNumber;
    }

    public int getTourPage() {
        return fTourPage;
    }

    public AbstractTourFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        fTourNumber = args.getInt(ARG_TOUR_NUMBER);
        fTourPage = args.getInt(ARG_TOUR_PAGE);
        fFragmentId = args.getString(ARG_TOUR_FRAGMENT_ID, null);
        if (fFragmentId == null) {
            throw new NullPointerException();
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fPersistKey = fFragmentId + KEY_POSTFIX;
        fIsComplete = settings.getBoolean(fPersistKey, false);
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
        if (!fAlwaysShowNextButton && !fIsComplete) {
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
        if (!fIsComplete) {
            fIsComplete = true;
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            Editor edit = settings.edit();
            edit.putBoolean(fPersistKey, true);
            edit.commit();
        }
    }

}
