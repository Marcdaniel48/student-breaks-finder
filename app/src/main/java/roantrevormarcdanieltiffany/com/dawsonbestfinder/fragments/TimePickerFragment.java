package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.CalendarActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.ChooseTeacherActivity;

/**
 * TimePicker fragment that extends DialogFragment and returns a TimePickerDialog
 * from the fragment's onCreateDialogMethod()
 *
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class TimePickerFragment extends DialogFragment {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "Called onCreateDialog");
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (CalendarActivity) getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
