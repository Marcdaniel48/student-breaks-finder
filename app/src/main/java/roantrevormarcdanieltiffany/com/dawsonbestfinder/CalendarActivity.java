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
    private EditText eventTitle;
    private EditText etDate;
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
        eventTitle = findViewById(R.id.etEvent);
    }

    public void showTimePickerDialog(View view) {
        Log.d(TAG, "called showTimePickerDialog()");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

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

    public void clickStartTime(View view) {
        Log.d(TAG, "called clickStartTime()");
        showTimePickerDialog(view);
        clickedStart = true;
    }

    public void clickEndTime(View view) {
        Log.d(TAG, "called clickEndTime()");
        showTimePickerDialog(view);
        clickedStart = false;
    }

    /**
     * @todo Check what to put inside calendar event (title/description/location/etc)
     * @param view
     */
    public void clickAddToCalendar(View view) {
        Log.d(TAG, "called clickAddToCalendar()");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, startHour, startMinute);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, endHour, endMinute);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, eventTitle.getText().toString());

        /**
         *                 .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
         .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym");
         */
        startActivity(intent);

    }
}
