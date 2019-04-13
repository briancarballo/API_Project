package edu.quinnipiac.ser210.hashtagchecker;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Input Fragment Class
 * Author: Brian Carballo
 * SER210
 *
 * Class encapsulates all UI elements of the input screen. Includes an interface for communication
 * with the activity it is attached to.
 */
public class InputFragment extends Fragment implements View.OnClickListener {

    Button checkButton;
    EditText input;
    TextView hashtagIcon, inputHelpText;

    @Override
    //Calls activity's onClick method
    public void onClick(View v) {
        listener.onClick(v);
    }

    //Allows for communication with activity
    static interface Listener {
        void onClick(View v);
    }


    private Listener listener;
    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Creates a reference to activity
        listener = (Listener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_input, container, false);
        checkButton = (Button) layout.findViewById(R.id.checkButton);
        hashtagIcon = (TextView) layout.findViewById(R.id.hashtag_icon);
        inputHelpText = (TextView) layout.findViewById(R.id.input_screen_helptext);
        input = (EditText) layout.findViewById(R.id.input);

        checkButton.setOnClickListener(this);
        return layout;
    }

    //Changes all fragment elements to color selected
    public void setDarkMode(Boolean darkMode){
        if (!darkMode) {
            getView().setBackgroundColor(Color.GRAY);
            input.setTextColor(Color.WHITE);
            input.setHintTextColor(Color.WHITE);
            hashtagIcon.setTextColor(Color.WHITE);
            inputHelpText.setTextColor(Color.WHITE);
        }
        else {
            getView().setBackgroundColor(Color.WHITE);
            input.setTextColor(Color.BLACK);
            input.setHintTextColor(Color.BLACK);
            hashtagIcon.setTextColor(Color.BLACK);
            inputHelpText.setTextColor(Color.BLACK);
        }
    }

}
