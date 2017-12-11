package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.FriendLocation;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.QueryParam;

/**
 * Displays the location of a friend.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class ItemFriendActivity extends Activity
{
    private static final String TAG = ItemFriendActivity.class.getSimpleName();

    private static final String EMAIL_KEY = "email";

    TextView tvFriendCourse, tvFriendSection;

    /**
     * onCreate for the ItemFriendActivity class
     * Executes an Async Task class to display the current course and section location of the
     * user's friend.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemfriends);

        tvFriendCourse = findViewById(R.id.tvFriendCourse);
        tvFriendSection = findViewById(R.id.tvFriendSection);

        new FindFriendLocationAsyncTask().execute();
    }

    /**
     * Async Task class that makes API calls to get the current course and section location of one of the current user's friends.
     */
    public class FindFriendLocationAsyncTask extends AsyncTask<Void, Void, FriendLocation>
    {
        /**
         * Attempts to get the current location of the requested friend.
         * @param voids
         * @return
         */
        @Override
        protected FriendLocation doInBackground(Void... voids)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.doInBackground()");

            // SharedPreferences for the Settings activity are stored in "settings"
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

            // Goes through the SharedPreferences of the Settings activity to get the user's email and password.
            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            // getExtras to get the email of the requested user.
            String friendEmail = getIntent().getExtras().getString(EMAIL_KEY);

            // Gets the current day where 1 is monday and 7 is sunday.
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int dayInt = calendar.get(Calendar.DAY_OF_WEEK);
            dayInt = dayInt==1 ? 7 : dayInt-1;
            String day = String.valueOf(dayInt);

            // Gets the current time in HourseMinute format.
            DateFormat dateFormat = new SimpleDateFormat("HHmm");
            String time = dateFormat.format(today);

            // Logs the query parameters.
            Log.d(TAG, email + " " + password + " " + friendEmail + " " + day + " " + time);

            try
            {
                // Query parameters needed to make a friend location API call.
                List<QueryParam> queryParams = new ArrayList<>();

                // The current user's email and password, according to the Settings SharedPreferences.
                queryParams.add(new QueryParam(FriendFinder.EMAIL_KEY, email));
                queryParams.add(new QueryParam(FriendFinder.PASSWORD_KEY, password));

                // The email of the current user's friend.
                queryParams.add(new QueryParam(FriendFinder.FRIEND_EMAIL_KEY, friendEmail));

                // Current day (1 to 7) and time (HHmm)
                queryParams.add(new QueryParam(FriendFinder.DAY_KEY, day));
                queryParams.add(new QueryParam(FriendFinder.TIME_KEY, time));

                URL url = NetworkUtils.buildUrl(FriendFinder.FRIEND_LOCATION_URL, queryParams);

                String json = NetworkUtils.getResponseFromHttpUrl(url);

                return FriendFinder.getFriendLocationFromJSON(json);
            }
            catch (MalformedURLException e)
            {
                Log.e(TAG, "MalformedURLException in FindFriendsAsyncTask.doInBackground");
            }
            catch (IOException e)
            {
                Log.e(TAG, "IOException in FindFriendsAsyncTask.doInBackground");
            }
            catch (JSONException e)
            {
                Log.e(TAG, "JSONException in FindFriendsAsyncTask.doInBackground");
            }

            Log.e(TAG, "doInBackground Returning empty location. If that happens, then something in the PHP side is not working correctly.");
            return new FriendLocation();
        }

        // onPostExecute, display the current course and section of the requested friend.
        @Override
        protected void onPostExecute(FriendLocation location)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.onPostExecute()");

            if(!location.getCourse().isEmpty())
                tvFriendCourse.setText(location.getCourse());
            else
                tvFriendCourse.setText(R.string.itemfriend_nocourse);

            if(!location.getSection().isEmpty())
                tvFriendSection.setText(location.getSection());
            else
                tvFriendSection.setText(R.string.itemfriend_nosection);
        }
    }

}
