package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TimePickerFragment;

/**
 * Created by mrtvor on 2017-12-06.
 */

public class WhoIsFreeActivity extends MenuActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = WhoIsFreeActivity.class.getSimpleName();

    private boolean clickedStart;
    private Spinner daySpinner;
    private EditText etBreakStart, etBreakEnd;
    private ListView lvFriends;

    private int day, hStart, hEnd, mStart, mEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_is_free);

        etBreakStart = findViewById(R.id.etBreakStart);
        etBreakEnd = findViewById(R.id.etBreakEnd);
        daySpinner = findViewById(R.id.daySpinner);
        lvFriends = findViewById(R.id.lvFriends);

        setDaySpinner();

        lvFriends.setVisibility(ListView.GONE);
    }

    private void setDaySpinner() {
        String[] days = getResources().getStringArray(R.array.days);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);

        daySpinner.setAdapter(adapter);
        daySpinner.setSelection(adapter.getPosition(getString(R.string.def_day)));
    }

    public void showTimePickerDialog(View v) {
        Log.d(TAG, "showTimePickerDialog()");
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "onTimeSet()");

        DateFormat simp = new SimpleDateFormat("hh:mm", Locale.getDefault());
        Date date = null;
        try {
            date = simp.parse(hourOfDay + ":" + minute);
        } catch (ParseException pe) {
            Log.e(TAG, pe.getMessage());
        }

        String time = simp.format(date);

        if(clickedStart) {
            etBreakStart.setText(time);
            this.hStart = hourOfDay;
            this.mStart = minute;
        } else {
            etBreakEnd.setText(time);
            this.hEnd = hourOfDay;
            this.mEnd = minute;
        }
    }

    public void clickBreakStart(View v) {
        Log.d(TAG, "clickBreakStart()");
        showTimePickerDialog(v);
        clickedStart = true;
    }

    public void clickBreakEnd(View v) {
        Log.d(TAG, "clickBreakEnd()");
        showTimePickerDialog(v);
        clickedStart = true;
    }

    private void loadFriendsFreeData() {
        Log.d(TAG, "loadFriendsFreeData()");
        if(etBreakStart.getText().toString().trim().equals("") || etBreakEnd.getText().toString().trim().equals("")) {
            Log.d(TAG, "No start or end time provided");
            //toast popup
            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(prefs.getString("EMAIL", "") == "" || prefs.getString("PASSWORD", "") == "") {
            Log.d(TAG, "No email or password provided");
            //toast popup
            return;
        }
        new OpenFriendsFreeTask().execute(prefs.getString("EMAIL", ""),
                prefs.getString("PASSWORD", ""), daySpinner.getSelectedItem().toString(),
                etBreakStart.getText().toString().replace(":",""),
                etBreakEnd.getText().toString().replace(":",""));
    }

    public class OpenFriendsFreeTask extends AsyncTask<String, Void, String[]> {
        private final String TAG = OpenFriendsFreeTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... strings) {
            Log.d(TAG, "doInBackground()");

            if(strings.length == 0) {
                Log.d(TAG, "No params");
                return new String[]{};
            }

            String day = strings[2];

            switch(day) {
                case "Tuesday":
                    day = "2";
                    break;
                case "Wednesday":
                    day = "3";
                    break;
                case "Thursday":
                    day = "4";
                    break;
                case "Friday":
                    day = "5";
                    break;
                default:
                    day = "1";
                    break;
            }

            String[] params = {strings[0], strings[1], day, strings[3], strings[4]};
            URL url = NetworkUtils.buildWhoIsFreeUrl(params);

            try {
                Log.d(TAG, "URL: " + url);
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                return
            }
            return new String[0];
        }
    }

}
