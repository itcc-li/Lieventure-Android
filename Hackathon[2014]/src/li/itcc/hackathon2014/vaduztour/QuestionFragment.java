
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.Settings.System;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class QuestionFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    
    public static QuestionFragment newInstance(int tourNumber, int tourPage) {
        QuestionFragment fragment = new QuestionFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        return fragment;
    }

    public QuestionFragment() {     
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container,
                false);
        Button nextButton = (Button)rootView.findViewById(R.id.next_button);               
        setNextButton(nextButton);
        
        Spinner story2_spinner = (Spinner)rootView.findViewById(R.id.spinner_question);
        ArrayAdapter<CharSequence> story2_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.story2_array, android.R.layout.simple_spinner_dropdown_item);
        story2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        story2_spinner.setAdapter(story2_adapter);                   
        
        return rootView;        
    }
    
    public void check_result(View view) {
        // Do something in response to button click
        //Log.i("Stefan", "Hier bin ich");
    }
    
}
