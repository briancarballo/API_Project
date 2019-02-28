package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.startButton);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, InputScreen.class);
        startActivity(intent);
    }
}
