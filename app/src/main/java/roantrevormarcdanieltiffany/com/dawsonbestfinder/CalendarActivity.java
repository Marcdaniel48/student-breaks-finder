package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.DatePickerFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TimePickerFragment;

/**
 * Activity which adds an event to the calendar.
 * The user can specify a date, start time, end time
 * and the event itself. Uses a calendar provider
 *
 * {@see https://developer.android.com/guide/topics/providers/calendar-provider.html}
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class CalendarActivity extends MenuActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    private boolean clickedStart;
    private EditText etTitle;
    private EditText etDate;
    private EditText etDescription;
    private EditText etLocation;
    private EditText etStartTime;
    private EditText etStopTime;
    private int year, month, day, startHour, startMinute, endHour, endMinute;

    /**
     * When invoked, will set up the activity.
     * Assigns the list view, get the data from firebase
     * and set the list of category titles
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        etDate = findViewById(R.id.etDate);
        etStartTime = findViewById(R.id.etStartTime);
        etStopTime = findViewById(R.id.etStopTime);
        etTitle = findViewById(R.id.etEventTitle);
        etDescription = findViewById(R.id.etEventDescription);
        etLocation = findViewById(R.id.etEventLocation);
    }

    /**
     * Shows  {@code DialogFragment}, {@code TimePickerFragment},
     * in which the user can select a time
     *
     * @param view
     */
    public void showTimePickerDialog(View view) {
        Log.d(TAG, "called showTimePickerDialog()");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * Shows {@code DialogFragment}, {@code DatePickerFragment},
     * in which the user can select a date
     *
     * @param view
     */
    public void showDatePickerDialog(View view) {
        Log.d(TAG, "called showDatePickerDialog()");
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
        Log.d(TAG, "called onDateSet()");
        this.year = year;
        this.month = month;
        day = dayOfMonth;

        // Format date to display
        DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(dayOfMonth + "/" + month + "/" + year);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        String formattedDate = sdf.format(date);

        // Display date
        etDate.setText(formattedDate);
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
        Log.d(TAG, "called onTimeSet()");

        // Format time to display
        DateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(hourOfDay + ":" + minute);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        String time = sdf.format(date);

        // Display on EditText clicked
        if (clickedStart) {
            etStartTime.setText(time);
            this.startHour = hourOfDay;
            this.startMinute = minute;
        } else {
            etStopTime.setText(time);
            this.endHour = hourOfDay;
            this.endMinute = minute;
        }
    }

    /**
     * Handler for start time. Opens the time picker dialog
     * and changes {@code clickedStart} to true.
     *
     * @param view
     */
    public void clickStartTime(View view) {
        Log.d(TAG, "called clickStartTime()");
        showTimePickerDialog(view);
        clickedStart = true;
    }

    /**
     * Handler for end time EditText. Opens the time picker dialog
     * and changes @code{clickStart} to false
     *
     * @param view
     */
    public void clickEndTime(View view) {
        Log.d(TAG, "called clickEndTime()");
        showTimePickerDialog(view);
        clickedStart = false;
    }

    /**
     * Handler which adds an event to the default Calendar.
     *
     * NOTE: This does not do validation since it's a picker and
     * the default Calendar automatically handles if the time range
     * is incorrect or if the user does not fill something.
     * It also does NOT automatically insert the event in the calendar,
     * but rather lets the user decide if they want to add it instead.
     * If we wanted to write to the Calendar, we would have to add that
     * permission to the manifest and change this method a bit.
     *
     * @todo Check what to put inside calendar event
     * @body For now it's title, description and location.
     *
     * @param view
     */
    public void clickAddToCalendar(View view) {
        Log.d(TAG, "called clickAddToCalendar()");
        // Prep event
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, startHour, startMinute);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, endHour, endMinute);

        // Set intent and put extras
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, etTitle.getText().toString())
                .putExtra(CalendarContract.Events.DESCRIPTION, etDescription.getText().toString())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, etLocation.getText().toString());

        startActivity(intent);

    }
}
