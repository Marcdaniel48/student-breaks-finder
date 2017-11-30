package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    /**
     * When invoke, will set up the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //Intent i = new Intent();
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
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to academic calendar activity
     */
    public void onAcademicCalendar(View v) {
        //Intent i = new Intent();
    }

    /**
     * Checks if the app has been used before. If a device runs this app for the first time, return true.
     * The way first startup is determined is by checking SharedPreferences for any user information.
     *
     * @return
     */
    public boolean firstStartUp()
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        if(prefs.contains("firstName") && prefs.contains("lastName") && prefs.contains("email") && prefs.contains("password") && prefs.contains("datestamp"))
            return false;

        return true;
    }
}


