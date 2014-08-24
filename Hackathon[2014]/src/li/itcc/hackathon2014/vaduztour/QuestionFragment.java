
package li.itcc.hackathon2014.vaduztour;

import li.itcc.hackathon2014.AbstractTourFragment;
import li.itcc.hackathon2014.R;
import android.os.Bundle;
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
    private Spinner question_spinner=null;
    private String question_stage="first";
    private String answer=null;
    private Button checkButton=null;
    
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
        //Setup View
        rootView = inflater.inflate(R.layout.fragment_question, container,
                false);
        resultView=(TextView)rootView.findViewById(R.id.section_label);
        resultView.setText(getResources().getString(R.string.question_first_text));
        answer = getResources().getString(R.string.question_first_answer);
        
        //Setup Buttons
        Button nextButton = (Button)rootView.findViewById(R.id.next_button);
        checkButton = (Button)rootView.findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClick((Button) v);
            }
        });                
        setNextButton(nextButton);
        
        //Setup Drop-Down-Box
        question_spinner = (Spinner)rootView.findViewById(R.id.spinner_question);
        ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_first_choises, android.R.layout.simple_spinner_dropdown_item);
        choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question_spinner.setAdapter(choises_adapter);                                 
        return rootView;        
    }
    
    private void onButtonClick(Button v) {
        String compare = String.valueOf(question_spinner.getSelectedItemId()+1);
        //Debug: resultView.setText(compare+" "+answer);
       
        if (compare.equals(answer)) {
            resultView.setText(question_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.question_right));
            if (question_stage.equals("first")){
                question_stage = "second";                
                resultView.append("\n\n"+getResources().getString(R.string.question_second_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_second_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_second_answer); 
            } else if (question_stage.equals("second")) {
                question_stage = "third";
                resultView.append("\n\n"+getResources().getString(R.string.question_third_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_third_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_third_answer);
            } else {
                checkButton.setOnClickListener(null);
                checkButton.setVisibility(View.INVISIBLE);
                resultView.append(getResources().getString(R.string.question_press_next));
                onTaskSolved();
            }                               
        } else {
            resultView.append("\n"+question_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.question_wrong));
        }
    }
    
}
