package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;

/**
 * Created by mrtvor on 2017-11-20.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.aboutItem:
                displayAbout();
                return true;
            case R.id.settingsItem:
                displaySettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate((int)R.layout.menu, menu);
        return true;
    }

    private void displayAbout() {
//        Intent intent = new Intent(this, AboutActivity.class);
//        startActivity(intent);
    }

    private void displaySettings() {
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
    }
}
