package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.os.Bundle;


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
    public void onDawsonButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Will fire an intent taking user to class cancellations activity
     */
    public void onClassCancellations() {
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to find teacher activity
     */
    public void onFindTeacher() {
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to add to calendar activity
     */
    public void onAddToCalendar() {
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to notes activity
     */
    public void onNotes() {
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to weather activity
     */
    public void onWeather() {
        //Intent i = new Intent();
    }

    /**
     * Will fire an intent taking user to academic calendar activity
     */
    public void onAcademicCalendar() {
        //Intent i = new Intent();
    }
}


