
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
import android.widget.TextView;

public class QuestionFragment extends AbstractTourFragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    private View rootView=null;
    private TextView resultView=null;
    private Spinner story2_spinner=null;
    
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
        rootView = inflater.inflate(R.layout.fragment_question, container,
                false);
        Button nextButton = (Button)rootView.findViewById(R.id.next_button);
        Button checkButton = (Button)rootView.findViewById(R.id.check_button);

        checkButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onButtonClick((Button) v);
            }
        });
                
        setNextButton(nextButton);
        resultView=(TextView)rootView.findViewById(R.id.section_label);
        
        story2_spinner = (Spinner)rootView.findViewById(R.id.spinner_question);
        ArrayAdapter<CharSequence> story2_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.story2_choises, android.R.layout.simple_spinner_dropdown_item);
        story2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        story2_spinner.setAdapter(story2_adapter);                   
        
        return rootView;        
    }
    
    private void onButtonClick(Button v) {
        String compare = String.valueOf(story2_spinner.getSelectedItemId()+1); // String mit Inhalt 0 - 3
        String answer  = getResources().getString(R.string.story2_answer);
        resultView.setText(compare+" "+answer);
        
        if (compare.equals(answer) || answer.equals("0")) {
            resultView.setText(story2_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.right));
        } else {
            resultView.setText(story2_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.wrong));
        }
    }
    
}
