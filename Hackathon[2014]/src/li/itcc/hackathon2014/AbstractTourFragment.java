
package li.itcc.hackathon2014;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.Button;

public abstract class AbstractTourFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_TOUR_NUMBER = "tour_number";
    private static final String ARG_TOUR_PAGE = "tour_page";
    
    public void setTourArguments(int tourNumber, int tourPage) {
        Bundle args = new Bundle();
        args.putInt(ARG_TOUR_NUMBER, tourNumber);
        args.putInt(ARG_TOUR_PAGE, tourPage);
        this.setArguments(args);
    }

    public AbstractTourFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle args = getArguments();
        int tourNumber = args.getInt(ARG_TOUR_NUMBER);
        int tourPage = args.getInt(ARG_TOUR_PAGE);
        ((MainActivity) activity).onFragmentAttached(this, tourNumber, tourPage);
    }
    
    
    public void setNextButton(Button nextButton) {
        
    }
    
    

}
