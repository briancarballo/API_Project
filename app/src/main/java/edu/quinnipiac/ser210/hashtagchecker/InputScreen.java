package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InputScreen extends AppCompatActivity implements View.OnClickListener{

    EditText text;
    String url1 = "https://tagdef.p.rapidapi.com/one.";
    String url2 = ".json";
    String hashtag;
    DefinitionHandler handler = new DefinitionHandler();
    private final String LOG_TAG = InputScreen.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Button check = (Button) findViewById(R.id.checkButton);


    }

    @Override
    public void onClick(View v) {
        text = (EditText) findViewById(R.id.input);
        new HashtagCheck().execute(String.valueOf(text.getText()));
        //Intent intent = new Intent(this, ResultScreen.class);
        //intent.putExtra("key", text.getText().toString());
        //startActivity(intent);
    }

    private class HashtagCheck extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String hashtagDefinition = null;

            try{
                URL url = new URL(url1 + strings[0] + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","cbcc14d80fmsh27971fc9d28845ep1daac9jsnda7cb1cd6ab2");
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                if(in == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));
                String hashtagDefinitionJsonString = getBufferStringFromBuffer(reader).toString();
                Log.d("JSON", hashtagDefinitionJsonString);
                hashtagDefinition = handler.getDefinition(hashtagDefinitionJsonString);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    } catch (IOException e){
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                        return null;
                    }
                }
            }
            return hashtagDefinition;
        }

        protected void onPostExecute(String result){
            if (result != null){
                Log.d(LOG_TAG, result);

                Intent intent = new Intent(InputScreen.this,ResultScreen.class);
                intent.putExtra("hashtagDefinition",result);

                startActivity(intent);

            }
        }

        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            StringBuffer buffer = new StringBuffer();

            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            return buffer;
        }
    }


}
