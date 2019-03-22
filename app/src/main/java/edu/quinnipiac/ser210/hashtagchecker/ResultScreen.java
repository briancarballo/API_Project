package edu.quinnipiac.ser210.hashtagchecker;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultScreen extends AppCompatActivity {

    private ShareActionProvider provider;
    private ConstraintLayout layout;
    private boolean darkMode;
    private TextView text;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        Intent intent = getIntent();
        result = intent.getStringExtra("hashtagDefinition");
        text = (TextView) findViewById(R.id.resultTextbox);
        text.setText(result);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        layout = (ConstraintLayout) findViewById(R.id.resultLayout);
        darkMode = false;
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
                    darkMode = !darkMode;
                }
                else {
                    layout.setBackgroundColor(Color.WHITE);
                    text.setTextColor(Color.BLACK);
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
                intent.putExtra(Intent.EXTRA_TEXT, result);
                provider.setShareIntent(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
