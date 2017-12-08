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
 * Fragment that extends {@code DialogFragment} and returns a {@code TimePickerDialog}
 * from the fragment's {@code onCreateDialogMethod()} with the parent's {@code CalendarActivity}
 * listener
 *
 * In part from {@see https://developer.android.com/guide/topics/ui/controls/pickers.html}
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class TeacherTimePickerFragment extends DialogFragment {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();

    /**
     * Custom {@code Dialog} Container
     *
     * @param savedInstanceState
     * @return {@code Dialog}
     *      with parent listener
     */
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
