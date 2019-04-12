package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import java.net.URL;

/**
 * Input Screen Activity
 * Author: Brian Carballo
 * SER210
 *
 * Class contains input methods for user to put in a hashtag to be searched. Activity communicates
 * with API and passes on a JSON string to a handler. The handler output is then passed on to the
 * next activity
 */
public class InputScreen extends AppCompatActivity implements InputFragment.Listener{

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
    private Button check;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_input));

        //Initiates elements from xml and creates java equivalents

        if (savedInstanceState == null){
            fragment = new InputFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.inputContainer, fragment);
            ft.addToBackStack(null);
            ft.commit();

            fragment = getSupportFragmentManager().findFragmentById(R.id.inputContainer);
//            check = (Button) findViewById(R.id.checkButton);
//            layout = (ConstraintLayout) findViewById(R.id.input_layout);
//            hashtagicon = (TextView) findViewById(R.id.hashtag_icon);
//            helpText = (TextView) findViewById(R.id.input_screen_helptext);
//            text = (EditText) findViewById(R.id.input);
        }

    }

    @Override
    public void onClick(View v) {
        //When the button is clicked, the definition for the hashtag from editText is
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.inputContainer);
        new HashtagCheck().execute(String.valueOf(((EditText) fragment.getView().findViewById(R.id.input)).getText()));
    }

    //Process where the hashtag definition is checked
    private class HashtagCheck extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String hashtagDefinition = null;

            //Attempts to open a connection
            try{
                URL url = new URL(url1 + strings[0] + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","cbcc14d80fmsh27971fc9d28845ep1daac9jsnda7cb1cd6ab2");
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();

                //checks if there exists a result
                if(in == null) {
                    return null;
                }

                //Reads input
                reader = new BufferedReader(new InputStreamReader(in));
                String hashtagDefinitionJsonString = getBufferStringFromBuffer(reader).toString();

                //Passes JSON to handler for parsing
                hashtagDefinition = handler.getDefinition(hashtagDefinitionJsonString);

            } catch (Exception e) {
                e.printStackTrace();
                return null;

                //Closes URL connection and reader once done
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

            //Returns final definition to be displayed
            return hashtagDefinition;
        }

        protected void onPostExecute(String result){
            //Checks if there is something to pass before starting result activity
            if (result != null){

                //Starts next activity and passes the definition
                Intent intent = new Intent(InputScreen.this,ResultScreen.class);
                intent.putExtra("hashtagDefinition",result);
                startActivity(intent);

            }
        }

        //Method for creating JSON String
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


    //Methods for instantiating the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MenuItem shareItem =  menu.findItem(R.id.action_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.inputContainer);
        switch (id){
            case R.id.action_color:
                if (!darkMode) {

                    fragment.getView().setBackgroundColor(Color.GRAY);
                    ((EditText) fragment.getView().findViewById(R.id.input)).setTextColor(Color.WHITE);
                    ((EditText) fragment.getView().findViewById(R.id.input)).setHintTextColor(Color.WHITE);
                    ((TextView) fragment.getView().findViewById(R.id.hashtag_icon)).setTextColor(Color.WHITE);
                    ((TextView) fragment.getView().findViewById(R.id.input_screen_helptext)).setTextColor(Color.WHITE);
                    darkMode = !darkMode;
                }
                else {
                    fragment.getView().setBackgroundColor(Color.WHITE);
                    ((EditText) fragment.getView().findViewById(R.id.input)).setTextColor(Color.BLACK);
                    ((EditText) fragment.getView().findViewById(R.id.input)).setHintTextColor(Color.BLACK);
                    ((TextView) fragment.getView().findViewById(R.id.hashtag_icon)).setTextColor(Color.BLACK);
                    ((TextView) fragment.getView().findViewById(R.id.input_screen_helptext)).setTextColor(Color.BLACK);
                    darkMode = !darkMode;
                }
                return  true;
            case R.id.help:
                Toast.makeText(this,"Type in a hashtag and the most popular definition will appear",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_share:
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
