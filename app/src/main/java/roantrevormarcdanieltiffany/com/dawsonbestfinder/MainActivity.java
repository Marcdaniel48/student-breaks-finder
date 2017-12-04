package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


/**
 * Main activity that displays all the image buttons, the menu, and
 * the current temperature
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class MainActivity extends MenuActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // For retrieving data from Settings SharedPreferences.
    protected static final String FIRST_NAME = "firstName";
    protected static final String LAST_NAME = "lastName";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";

    // Name for Settings SharedPreferences
    protected static final String SETTINGS = "settings";
  
    /**
     * When invoke, will set up the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences for the Settings activity are stored in "settings"
        SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);

        // If the user has no account credentials stored, open the settings activity so that he or she can enter and save them.
        if(!prefs.contains(FIRST_NAME) && !prefs.contains(LAST_NAME) && !prefs.contains(EMAIL) && !prefs.contains(PASSWORD))
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

    }

    /**
     * Will fire an intent taking user to dawson homepage
     */
    public void onDawsonButton(View v) {
        String url = "https://dawsoncollege.qc.ca";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Will fire an intent taking user to class cancellations activity
     */
    public void onClassCancellations(View v) {
        Log.d("BUTTON PRESS HANDLER", "Button clicked");
        Intent cci = new Intent(MainActivity.this, CancelledClassActivity.class);
        MainActivity.this.startActivity(cci);
    }

    /**
     * Will fire an intent taking user to find teacher activity
     */
    public void onFindTeacher(View v) {
        Intent intent = new Intent(this, FindTeacherActivity.class);
        startActivity(intent);
    }

    /**
     * Will fire an intent taking user to add to calendar activity
     */
    public void onAddToCalendar(View v) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    /**
     * Will fire an intent taking user to notes activity
     */
    public void onNotes(View v) {
        Intent i = new Intent(this, NotesActivity.class);
        startActivity(i);
    }

    /**
     * Will fire an intent taking user to weather activity
     */
    public void onWeather(View v) {
        Intent i = new Intent(this, WeatherActivity.class);
        startActivity(i);
    }

    /**
     * Will fire an intent taking user to academic calendar activity
     */
    public void onAcademicCalendar(View v) {
        //Intent i = new Intent();
    }
}


