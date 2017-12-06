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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;

public class FindFriendsActivity extends MenuActivity
{
    private static final String TAG = FindFriendsActivity.class.getSimpleName();
    ListView friendsLV;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "Called onCreate(Bundle savedInstanceState)");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        friendsLV = findViewById(R.id.friendsListView);
        new FindFriendsAsyncTask().execute();

    }

    public class FindFriendsAsyncTask extends AsyncTask<Void, Void, List<Friend>>
    {
        @Override
        protected List<Friend> doInBackground(Void... voids)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.doInBackground()");

            // SharedPreferences for the Settings activity are stored in "settings"
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            try
            {
                URL url = FriendFinder.buildFindFriendsURL(email, password);
                String json = OpenWeather.getResponseFromHttpUrl(url);

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

            Log.e(TAG, "doInBackground Returning null");
            return null;
        }

        @Override
        protected void onPostExecute(final List<Friend> friends)
        {
            Log.d(TAG, "Called FindFriendsAsyncTask.onPostExecute()");

           ArrayAdapter<Friend> adapter = new ArrayAdapter<>(FindFriendsActivity.this, R.layout.listview_friend, R.id.friendTextView, friends);

           //friendsLV.setAdapter(adapter);

            friendsLV.setAdapter(new BaseAdapter()
            {
                @Override
                public int getCount() {
                    return 0;
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup)
                {
                    LayoutInflater inf = (LayoutInflater) FindFriendsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inf.inflate(R.layout.listview_friend, null);

                    TextView tv = view.findViewById(R.id.friendTextView);;
                    view.setTag(tv);
                    tv.setText(friends.get(i).toString());

                    tv.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View view)
                        {
                            Intent intent = new Intent(FindFriendsActivity.this, null);
                        }
                    });

                    return view;
                }
            });
        }
    }

}
