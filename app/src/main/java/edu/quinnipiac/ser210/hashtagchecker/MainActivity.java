package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Main Activity
 * Author: Brian Carballo
 * SER210
 *
 * Activity is the first activity to be opened. Serves as the splash screen and includes only a
 * start button and a picture
 */
public class MainActivity extends AppCompatActivity implements MainFragment.Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates and adds fragment to activity layout
        if (savedInstanceState == null){
            MainFragment fragment = new MainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.mainFragmentContainer, fragment, "FRAGMENT");
            ft.commit();
        }
    }

    @Override
    public void onClick(View v) {
        //Brings user to next screen
        Intent intent = new Intent(this, InputScreen.class);
        startActivity(intent);
    }
}
