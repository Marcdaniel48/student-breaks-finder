package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.Friend;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.QueryParam;


/**
 * Activity that shows the user a list of friends who are also in a class that has been cancelled.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 *
 */
public class CancelledFriendActivity extends MenuActivity {

    private static final String TAG = CancelledFriendActivity.class.getSimpleName();
    private static final String COURSENAME_KEY = "coursename";
    private static final String SECTION_KEY = "section";

    Context context;

    ListView friendInClassLV;

    /**
     * create the activity and get the info about who is in a cancelled class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_friend);

        context = this;

        friendInClassLV = findViewById(R.id.cancelledFriendsListView);

        //parameters to be sent with the request to the API
        String coursename = getIntent().getExtras().getString(COURSENAME_KEY);
        String section = getIntent().getExtras().getString(SECTION_KEY);

        //put variables into an array
        String[] params = {coursename, section};

        //create a new thread and pass the array of params to it
        new FindFriendsAsyncTask().execute(params);

    }

    /**
     * Async task to get a list of friends who are also taking the cancelled class and update the ui from the background
     *
     * @author Roan Chamberlain
     */
    public class FindFriendsAsyncTask extends AsyncTask<String, Void, List<Friend>>{

        /**
         * get the list of friends from the API call in the background given a course name and section number
         * @param params course name and section
         * @return List of friends to be used when updating the ui
         */
        @Override
        protected List<Friend> doInBackground(String ... params){

            String coursename = params[0];
            String section = params[1];

            Log.d(TAG, "doInBackground: Called");

            // SharedPreferences for the Settings activity are stored in "settings"
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);

            // Retrieves the email and password values
            String email = prefs.getString(SettingsActivity.EMAIL, "");
            String password = prefs.getString(SettingsActivity.PASSWORD, "");

            try{
                //set up the url to send to the api
                List<QueryParam> queryParams = new ArrayList<>();
                queryParams.add(new QueryParam(FriendFinder.EMAIL_KEY, email));
                queryParams.add(new QueryParam(FriendFinder.PASSWORD_KEY, password));
                queryParams.add(new QueryParam(FriendFinder.SECTION_KEY, section));
                queryParams.add(new QueryParam(FriendFinder.COURSE_NAME_KEY, coursename));

                URL url = NetworkUtils.buildUrl(FriendFinder.MUTUAL_CLASS_URL, queryParams);
                String JSONResponse = NetworkUtils.getResponseFromHttpUrl(url);

                return FriendFinder.getFriendsFromJSON(JSONResponse);

            }catch(Exception e){
                Log.e(TAG, "doInBackground: got an exeption");
                e.getStackTrace();
            }
            return new ArrayList<Friend>();
        }

        /**
         * update the UI with the list of friends that doInBackground returns
         * @param friends
         */
        @Override
        protected void onPostExecute(final List<Friend> friends){
            Log.d(TAG, "onPostExecute: called");

            //pop up a dialog if the user has no friends on break
            if(friends.isEmpty()){
                Log.i(TAG, "onPostExecute: The user has no friends on break");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.no_friends_in_course);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            //fill the listview with data from the list
            friendInClassLV.setAdapter(new BaseAdapter() {
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
                public View getView(final int position, View convertView, ViewGroup viewGroup)
                {
                    LayoutInflater inflater = (LayoutInflater) CancelledFriendActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listview_friend, null);

                    Holder holder = new Holder();

                    convertView.setTag(holder);

                    // Populates the friend list view with a bunch of friends.
                    holder.tv = convertView.findViewById(R.id.friendTextView);
                    holder.tv.setText(friends.get(position).toString());

                    holder.tv.setOnClickListener(new View.OnClickListener()
                    {

                        /**
                         * When the user clicks on a Friend, start an activity which displays the current location (current course & section) of that Friend
                         * @param view
                         */
                        @Override
                        public void onClick(View view)
                        {
                            Log.i(TAG, "onClick: the follwoing friend was just tapped : " + friends.get(position).getFirstname());
                            if (friends.get(position).getEmail().length() > 0) {
                                Intent i = new Intent(Intent.ACTION_SENDTO);
                                i.setData(Uri.parse("mailto:"));

                                i.putExtra(Intent.EXTRA_EMAIL, new String[] {friends.get(position).getEmail()});
                                i.putExtra(Intent.EXTRA_SUBJECT, "from: DaBestFinder");
                                startActivity(i);
                            }
                        }
                    });

                    return convertView;
                }

                class Holder
                {
                    TextView tv;
                }
            });
        }
        
    }


}
