package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        Intent intent = getIntent();
        String display = intent.getStringExtra("key");
        TextView text = (TextView) findViewById(R.id.resultTextbox);
        text.setText(text.getText() + " " + display);
    }
}
