package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InputScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText text;
    private String url1 = "https://tagdef.p.rapidapi.com/one.";
    private String url2 = ".json";
    private String hashtag;
    private TextView hashtagicon,helpText;
    private DefinitionHandler handler = new DefinitionHandler();
    private ShareActionProvider provider;
    private ConstraintLayout layout;
    private boolean darkMode;
    private final String LOG_TAG = InputScreen.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Button check = (Button) findViewById(R.id.checkButton);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_input));
        layout = (ConstraintLayout) findViewById(R.id.input_layout);
        hashtagicon = (TextView) findViewById(R.id.hashtag_icon);
        helpText = (TextView) findViewById(R.id.input_screen_helptext);
        text = (EditText) findViewById(R.id.input);

    }

    @Override
    public void onClick(View v) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        // Get the ActionProvider for later usage
        MenuItem shareItem =  menu.findItem(R.id.action_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_color:
                if (!darkMode) {
                    layout.setBackgroundColor(Color.GRAY);
                    text.setTextColor(Color.WHITE);
                    text.setHintTextColor(Color.WHITE);
                    hashtagicon.setTextColor(Color.WHITE);
                    helpText.setTextColor(Color.WHITE);
                    darkMode = !darkMode;
                }
                else {
                    layout.setBackgroundColor(Color.WHITE);
                    text.setTextColor(Color.BLACK);
                    text.setHintTextColor(Color.BLACK);
                    hashtagicon.setTextColor(Color.BLACK);
                    helpText.setTextColor(Color.BLACK);
                    darkMode = !darkMode;
                }
                return  true;
            case R.id.help:
                Toast.makeText(this,"Type in a hashtag and the most popular definition will appear",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_share:
                // populate the share intent with data
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, ".");
                provider.setShareIntent(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
