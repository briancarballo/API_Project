package edu.quinnipiac.ser210.hashtagchecker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

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
        listener = (Listener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_input, container, false);
        Button checkButton = (Button) layout.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(this);
        return layout;
    }

}
