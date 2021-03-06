package edu.quinnipiac.ser210.hashtagchecker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Main Fragment Class
 * Author: Brian Carballo
 * SER210
 *
 * Class encapsulates all UI elements of the main starting screen. Includes an interface to handle
 * button click.
 */

public class MainFragment extends Fragment implements View.OnClickListener {




    static interface Listener {
        void onClick(View v);
    }

    private Listener listener;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        Button start = (Button) layout.findViewById(R.id.startButton);
        start.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //creates reference to activity
        this.listener = (Listener)context;
    }

    //Calls activity's onclick method
    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
