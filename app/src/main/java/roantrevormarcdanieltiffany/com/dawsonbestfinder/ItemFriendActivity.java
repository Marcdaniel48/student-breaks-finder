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
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.FriendLocation;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;

public class ItemFriendActivity extends Activity
{
    private static final String TAG = ItemFriendActivity.class.getSimpleName();

    private static final String EMAIL_KEY = "email";

    TextView tvFriendCourse, tvFriendSection;

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

    public class FindFriendLocationAsyncTask extends AsyncTask<Void, Void, FriendLocation>
    {
        @Override
        protected FriendLocation doInBackground(Void... voids)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.doInBackground()");

            // SharedPreferences for the Settings activity are stored in "settings"
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            String friendEmail = getIntent().getExtras().getString(EMAIL_KEY);

            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int dayInt = calendar.get(Calendar.DAY_OF_WEEK);
            dayInt = dayInt==1 ? 7 : dayInt-1;
            String day = String.valueOf(dayInt);

            DateFormat dateFormat = new SimpleDateFormat("HHmm");
            String time = dateFormat.format(today);
            Log.d(TAG, email + " " + password + " " + friendEmail + " " + day + " " + time);
            try
            {
                List<QueryParam> queryParams = new ArrayList<>();
                queryParams.add(new QueryParam(FriendFinder.EMAIL_KEY, email));
                queryParams.add(new QueryParam(FriendFinder.PASSWORD_KEY, password));
                queryParams.add(new QueryParam(FriendFinder.FRIEND_EMAIL_KEY, friendEmail));
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

            Log.e(TAG, "doInBackground Returning null");
            return null;
        }

        @Override
        protected void onPostExecute(FriendLocation location)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.onPostExecute()");

            tvFriendCourse.setText(location.getCourse());
            tvFriendSection.setText(location.getSection());
        }
    }

}
