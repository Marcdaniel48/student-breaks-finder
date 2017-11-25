package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.DatePickerFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TimePickerFragment;

/**
 * Activity which adds an event to the calendar.
 * The user can specify a date, start time, end time
 * and the event itself. Uses a calendar provider
 *
 * @see "https://developer.android.com/guide/topics/providers/calendar-provider.html"
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class CalendarActivity extends MenuActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    private boolean clickedStart;
    /**
     * When invoked, will set up the activity.
     * Assigns the list view, get the data from firebase
     * and set the list of category titles
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@link Calendar#MONTH})
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //do some stuff for example write on log and update TextField on activity
        Log.d(TAG,"Date = " + year);
    }

    /**
     * Called when the user is done setting a new time and the dialog has
     * closed.
     *
     * @param view      the view associated with this listener
     * @param hourOfDay the hour that was set
     * @param minute    the minute that was set
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //do some stuff for example write on log and update TextField on activity
        Log.d(TAG,"Time = " + hourOfDay + ":" + minute);
        Log.d(TAG, "After start time: " + clickedStart);
    }

    public void clickStartTime(View view) {
        showTimePickerDialog(view);
        clickedStart = true;
    }

    public void clickEndTime(View view) {
        showTimePickerDialog(view);
        clickedStart = false;
    }
}
