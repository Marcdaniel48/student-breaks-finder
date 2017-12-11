package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.CalendarActivity;

/**
 * Fragment that extends {@code DialogFragment} and returns a {@code DatePickerDialog}
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
public class DatePickerFragment extends DialogFragment {
    private static final String TAG = DatePickerFragment.class.getSimpleName();

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
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (CalendarActivity) getActivity(), year, month, day);
    }

}
