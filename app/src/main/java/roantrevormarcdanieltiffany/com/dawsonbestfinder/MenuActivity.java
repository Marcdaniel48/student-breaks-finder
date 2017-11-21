package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

/**
 * Menu Activity containing about, and settings items
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Invoked when user selects a menu item and shows selected intent
     *
     * @param item
     * @return true if passed
     */
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

    /**
     * Called whenever a menu item is selected.
     *
     * @param menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate((int)R.layout.menu, menu);
        return true;
    }

    /**
     * Display about activity
     */
    private void displayAbout() {
//        Intent intent = new Intent(this, AboutActivity.class);
//        startActivity(intent);
    }

    /**
     * Display settings activity
     */
    private void displaySettings() {
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
    }
}
