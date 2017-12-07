package roantrevormarcdanieltiffany.com.dawsonbestfinder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;

/**
 * Class that handles retrieving a list of the current user's friends (according to the settings activity) and displaying that
 * list of friends into the FindFriends activity.
 */
public class FindFriendsActivity extends MenuActivity
{
    private static final String TAG = FindFriendsActivity.class.getSimpleName();

    ListView friendsLV;

    /**
     * onCreate
     *
     * Loads the layout elements of the FindFriend activity and makes an Async call to get the current user's friends and display them
     * in the FindFriends list view.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "Called onCreate(Bundle savedInstanceState)");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        friendsLV = findViewById(R.id.friendsListView);

        new FindFriendsAsyncTask().execute();
    }

    /**
     * Async Task class for the FriendFinder API.
     */
    public class FindFriendsAsyncTask extends AsyncTask<Void, Void, List<Friend>>
    {
        @Override
        protected List<Friend> doInBackground(Void... voids)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.doInBackground()");

            // SharedPreferences for the Settings activity are stored in "settings"
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

            // Retrieves the email and password values
            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            try
            {
                // Creates a list of query parameters.
                List<QueryParam> queryParams = new ArrayList<>();
                queryParams.add(new QueryParam(FriendFinder.EMAIL_KEY, email));
                queryParams.add(new QueryParam(FriendFinder.PASSWORD_KEY, password));

                // Builds a URL to make the API call to find the location of a friend.
                URL url = NetworkUtils.buildUrl(FriendFinder.FIND_FRIENDS_URL, queryParams);
                String json = NetworkUtils.getResponseFromHttpUrl(url);

                // Uses the JSON response from the API call to make and return a list of Friend objects.
                return FriendFinder.getFriendsFromJSON(json);
            }
            catch (MalformedURLException e)
            {
                Log.e(TAG, "MalformedURLException in FindFriendsAsyncTask.doInBackground");
            }
            catch (IOException e)
            {
                Log.e(TAG, "IOException in FindFriendsAsyncTask.doInBackground");
            } catch (JSONException e)
            {
                Log.e(TAG, "JSONException in FindFriendsAsyncTask.doInBackground");
            }

            return null;
        }

        /**
         * Populates the list view in the FindFriends activity with a list of Friend objects.
         * The first name, last name, email of each Friend is what's being displayed.
         * @param friends
         */
        @Override
        protected void onPostExecute(final List<Friend> friends)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.onPostExecute()");

            friendsLV.setAdapter(new BaseAdapter()
            {
                private final String EMAIL_KEY = "email";

                @Override
                public int getCount() {
                    return friends.size();
                }

                @Override
                public Object getItem(int i) {
                    return i;
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(final int i, View view, ViewGroup viewGroup)
                {
                    LayoutInflater inf = (LayoutInflater) FindFriendsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inf.inflate(R.layout.listview_friend, null);

                    Holder holder = new Holder();

                    view.setTag(holder);

                    // Populates the friend list view with a bunch of friends.
                    holder.tv = view.findViewById(R.id.friendTextView);
                    holder.tv.setText(friends.get(i).toString());

                    holder.tv.setOnClickListener(new View.OnClickListener()
                    {

                        /**
                         * When the user clicks on a Friend, start an activity which displays the current location (current course & section) of that Friend
                         * @param view
                         */
                        @Override
                        public void onClick(View view)
                        {
                            Intent intent = new Intent(FindFriendsActivity.this, ItemFriendActivity.class);

                            intent.putExtra(EMAIL_KEY, friends.get(i).getEmail());

                            startActivity(intent);
                        }
                    });

                    return view;
                }

                class Holder
                {
                    TextView tv;
                }
            });
        }
    }

}
