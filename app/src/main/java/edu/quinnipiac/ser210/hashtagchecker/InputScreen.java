package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputScreen extends AppCompatActivity implements View.OnClickListener{

    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Button check = (Button) findViewById(R.id.checkButton);


    }

    @Override
    public void onClick(View v) {
        text = (EditText) findViewById(R.id.input);
        Intent intent = new Intent(this, ResultScreen.class);
        intent.putExtra("key", text.getText().toString());
        startActivity(intent);
    }
}
