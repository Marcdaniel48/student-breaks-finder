package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.WhoIsFreeTimePickerFragment;

/**
 * Activity which will allow the user to specify a day of the week
 * and a start time and end time. A list of friends will display
 * with the option to click on any of them, firing an intent to
 * launch an email activity with the email being automatically
 * set as the recipient
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class WhoIsFreeActivity extends MenuActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = WhoIsFreeActivity.class.getSimpleName();

    private boolean clickedStart;
    private Spinner daySpinner;
    private EditText etBreakStart, etBreakEnd;
    private ListView lvFriends;
    private final String SEARCH_CLICKED = "searchClicker";
    private final String FRIENDS_LIST = "friendsList";
    private final String DAY = "day";
    private final String START_TIME = "startTime";
    private final String END_TIME = "endTime";
    private final String START_HOUR = "startHour";
    private final String START_MIN = "startMin";
    private final String END_HOUR = "endHour";
    private final String END_MIN = "endMin";
    private ArrayList<String> loadedFriends = new ArrayList<>();
    private int startHour, startMinute, endHour, endMinute;

    /**
     * Will set up the activity.
     * Instantiates fields and calls
     * method to set up the spinner
     *
     * @param savedInstanceState
     */
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

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(SEARCH_CLICKED)) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(WhoIsFreeActivity.this,
                        android.R.layout.simple_list_item_1, savedInstanceState.getStringArrayList(FRIENDS_LIST));
                loadedFriends = savedInstanceState.getStringArrayList(FRIENDS_LIST);
                lvFriends.setAdapter(adapter);
            }
            if(savedInstanceState.containsKey(START_TIME))
                etBreakStart.setText( savedInstanceState.getString(START_TIME));
            if(savedInstanceState.containsKey(END_TIME))
                etBreakEnd.setText(savedInstanceState.getString(END_TIME));
            if(savedInstanceState.containsKey(START_HOUR))
                startHour = savedInstanceState.getInt(START_HOUR);
            if(savedInstanceState.containsKey(START_MIN))
                startMinute = savedInstanceState.getInt(START_MIN);
            if(savedInstanceState.containsKey(END_HOUR))
                endHour = savedInstanceState.getInt(END_HOUR);
            if(savedInstanceState.containsKey(END_MIN))
                endMinute = savedInstanceState.getInt(END_MIN);
            if(savedInstanceState.containsKey(DAY))
                daySpinner.setSelection(savedInstanceState.getInt(DAY));

        }
    }

    /**
     * Set the values of the spinner to be
     * the days of the week
     */
    private void setDaySpinner() {
        String[] days = getResources().getStringArray(R.array.days);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);

        daySpinner.setAdapter(adapter);
        daySpinner.setSelection(adapter.getPosition(getString(R.string.def_day)));
    }

    /**
     * Displays the time picker dialog
     *
     * @param v
     */
    public void showTimePickerDialog(View v) {
        Log.d(TAG, "showTimePickerDialog()");
        DialogFragment timeFragment = new WhoIsFreeTimePickerFragment();
        timeFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * set the fields to the values of
     * the widgets.
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
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
            startHour = hourOfDay;
            startMinute = minute;
        } else {
            etBreakEnd.setText(time);
            endHour = hourOfDay;
            endMinute = minute;
        }
    }

    public void clickSearch(View v) {
        Log.d(TAG, "clickSearch()");
        loadFriendsFreeData();
    }

    public void clickBreakStart(View v) {
        Log.d(TAG, "clickBreakStart()");
        showTimePickerDialog(v);
        clickedStart = true;
    }

    public void clickBreakEnd(View v) {
        Log.d(TAG, "clickBreakEnd()");
        showTimePickerDialog(v);
        clickedStart = false;
    }

    /**
     * Save uvi and forecast to state bundle
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "called onSaveInstanceState()");
        super.onSaveInstanceState(outState);

        outState.putInt(SEARCH_CLICKED, lvFriends.getVisibility());
        outState.putStringArrayList(FRIENDS_LIST, loadedFriends);
        outState.putInt(DAY, daySpinner.getSelectedItemPosition());
        outState.putString(START_TIME, etBreakStart.getText().toString());
        outState.putString(END_TIME, etBreakEnd.getText().toString());
        outState.putInt(START_HOUR, startHour);
        outState.putInt(START_MIN, startMinute);
        outState.putInt(END_HOUR, endHour);
        outState.putInt(END_MIN, endMinute);

    }

    /**
     * Grab data from shared preferences, as well
     * as widgets. Then call OpenFriendsFreeTask
     * with all of the data
     */
    private void loadFriendsFreeData() {
        Log.d(TAG, "loadFriendsFreeData()");
        if(etBreakStart.getText().toString().trim().equals("") || etBreakEnd.getText().toString().trim().equals("")) {
            Log.d(TAG, "No start or end time provided");
            Toast.makeText(WhoIsFreeActivity.this, "Invalid Input",
                    Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

        if(prefs.getString( SettingsActivity.EMAIL, "") == "" || prefs.getString(SettingsActivity.PASSWORD, "") == "") {
            Log.d(TAG, "No email or password provided");
            Toast.makeText(WhoIsFreeActivity.this, "Invalid Input",
                    Toast.LENGTH_LONG).show();
            return;
        }
        new OpenFriendsFreeTask().execute(daySpinner.getSelectedItem().toString(),
                "" + startHour + startMinute,
                "" + endHour + endMinute);
    }

    /**
     * Async Task class for retrieving the who is free data
     */
    public class OpenFriendsFreeTask extends AsyncTask<String, Void, List<Friend>> {
        private final String TAG = OpenFriendsFreeTask.class.getSimpleName();

        @Override
        protected List<Friend> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground()");

            if (strings.length == 0) {
                Log.d(TAG, "No params");
                return null;
            }

            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);
            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            String day = strings[0];

            switch (day) {
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

            try {
                List<QueryParam> queryParams = new ArrayList<>();
                queryParams.add(new QueryParam(FriendFinder.EMAIL_KEY, email));
                queryParams.add(new QueryParam(FriendFinder.PASSWORD_KEY, password));
                queryParams.add(new QueryParam(FriendFinder.DAY_KEY, day));
                queryParams.add(new QueryParam(FriendFinder.BREAK_START, strings[1]));
                queryParams.add(new QueryParam(FriendFinder.BREAK_END, strings[2]));

                URL url = NetworkUtils.buildUrl(FriendFinder.WHO_IS_FREE_URL, queryParams);
                String json = NetworkUtils.getResponseFromHttpUrl(url);

                return FriendFinder.getFriendsFromJSON(json);
            } catch (IOException | JSONException err) {
                Log.e(TAG, err.getMessage());
            }
            return new ArrayList<>();
        }

        /**
         * Populates the list of friends.
         * On click it will open an email intent,
         * with the clicked friend as the filled
         * in recipient email and app name as the
         * subject
         *
         * @param friends
         */
        @Override
        protected void onPostExecute(final List<Friend> friends) {
            Log.d(TAG, "onPostExecute()");

            loadedFriends = new ArrayList<>();

            for(Friend fren : friends)
                loadedFriends.add(fren.toString());

            lvFriends.setAdapter(new BaseAdapter() {

                @Override
                public int getCount() {
                    return friends.size();
                }

                @Override
                public Object getItem(int position) {
                    return position;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = (LayoutInflater) WhoIsFreeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listview_friend, null);
                    Holder holder = new Holder();
                    convertView.setTag(holder);

                    holder.tv = convertView.findViewById(R.id.friendTextView);
                    holder.tv.setText(friends.get(position).toString());

                    holder.tv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String[] address = new String[1];
                            address[0] = friends.get(position).getEmail().trim();
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"));
                            intent.putExtra(Intent.EXTRA_EMAIL, address);
                            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                            if(intent.resolveActivity(getPackageManager()) != null)
                                startActivity(intent);
                        }
                    });
                    return convertView;
                }
                class Holder {
                    TextView tv;
                }
            });
        }
    }
}
