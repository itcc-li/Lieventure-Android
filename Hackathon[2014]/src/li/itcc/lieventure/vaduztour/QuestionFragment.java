
package li.itcc.lieventure.vaduztour;

import li.itcc.lieventure.R;
import li.itcc.lieventure.AbstractTourFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class QuestionFragment extends AbstractTourFragment {
    public static final int QUESTION_SET_WOMAN = 0;
    public static final int QUESTION_SET_CASTLE = 1;
    private static final String ARG_QUESTION_SET = "tour_question_set";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    private View rootView=null;
    private TextView resultView=null;
    private Spinner question_spinner=null;
    private String question_stage="first";
    private String answer=null;
    private Button checkButton=null;
    private Button nextButton=null;
    private int questionSet;
    
    public static QuestionFragment newInstance(int tourNumber, int tourPage, int questionSet) {
        QuestionFragment fragment = new QuestionFragment();
        fragment.setTourArguments(tourNumber, tourPage);
        fragment.getArguments().putInt(ARG_QUESTION_SET, questionSet);
        return fragment;
    }

    public QuestionFragment() {     
    }   
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        questionSet = getArguments().getInt(ARG_QUESTION_SET);
        //Setup View
        rootView = inflater.inflate(R.layout.fragment_question, container,
                false);
        
        resultView=(TextView)rootView.findViewById(R.id.question_text);
        if (questionSet == QUESTION_SET_WOMAN) {
            resultView.setText(getResources().getString(R.string.question_first_text));
            answer = getResources().getString(R.string.question_first_answer);     
            question_spinner = (Spinner)rootView.findViewById(R.id.question_spinner);
            ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_first_choises, android.R.layout.simple_spinner_dropdown_item);
            choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            question_spinner.setAdapter(choises_adapter);                                 
        } else {
            question_stage = "second";
            resultView.setText(getResources().getString(R.string.question_second_text));
            answer = getResources().getString(R.string.question_second_answer);
            question_spinner = (Spinner)rootView.findViewById(R.id.question_spinner);
            ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_second_choises, android.R.layout.simple_spinner_dropdown_item);
            choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            question_spinner.setAdapter(choises_adapter); 
        }
        nextButton = (Button)rootView.findViewById(R.id.question_next_button);
        checkButton = (Button)rootView.findViewById(R.id.question_check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClick((Button) v);
            }
        });                
        return rootView;       
    }
    
    private void onButtonClick(Button v) {
        String compare = String.valueOf(question_spinner.getSelectedItemId()+1);  
        if (compare.equals(answer)) {
            resultView.setText(question_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.question_right));
            if (question_stage.equals("first")){                         
                checkButton.setOnClickListener(null);
                checkButton.setVisibility(View.INVISIBLE);
                setNextButton(nextButton);
                resultView.append(getResources().getString(R.string.question_press_next));                
                onTaskSolved();
            } else if (question_stage.equals("second")) {
                question_stage = "third";
                resultView.append("\n\n"+getResources().getString(R.string.question_third_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_third_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_third_answer);
            } else if (question_stage.equals("third")) {
                question_stage = "fourth";
                resultView.append("\n\n"+getResources().getString(R.string.question_fourth_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_fourth_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_fourth_answer);
            } else if (question_stage.equals("fourth")) {
                question_stage = "fifth";
                resultView.append("\n\n"+getResources().getString(R.string.question_fifth_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_fifth_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_fifth_answer);             
            } else if (question_stage.equals("fifth")) {
                question_stage = "sixth";
                resultView.append("\n\n"+getResources().getString(R.string.question_sixth_text));
                ArrayAdapter<CharSequence> choises_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.question_sixth_choises, android.R.layout.simple_spinner_dropdown_item);
                choises_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                question_spinner.setAdapter(choises_adapter);   
                answer = getResources().getString(R.string.question_sixth_answer);                   
            } else {
                checkButton.setOnClickListener(null);
                checkButton.setVisibility(View.INVISIBLE);
                resultView.append(getResources().getString(R.string.question_press_next));
                setNextButton(nextButton);
                onTaskSolved();
            }                               
        } else {
            resultView.append("\n"+question_spinner.getSelectedItem().toString()+" "+getResources().getString(R.string.question_wrong));
        }
    }
    
}
