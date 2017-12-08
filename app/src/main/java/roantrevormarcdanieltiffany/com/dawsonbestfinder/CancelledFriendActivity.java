package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;


import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.FriendFinder;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;

public class CancelledFriendActivity extends MenuActivity {

    private static final String TAG = CancelledFriendActivity.class.getSimpleName();
    private static final String COURSENAME_KEY = "coursename";
    private static final String SECTION_KEY = "section";

    Context context;

    ListView friendInClassLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_friend);

        context = this;

        friendInClassLV = findViewById(R.id.cancelledFriendsListView);

        //String coursename = savedInstanceState.getString(COURSENAME_KEY);
        //String section = savedInstanceState.getString(SECTION_KEY);
        String coursename = getIntent().getExtras().getString(COURSENAME_KEY);
        String section = getIntent().getExtras().getString(SECTION_KEY);

        String[] params = {coursename, section};

        new FindFriendsAsyncTask().execute(params);

    }

    public class FindFriendsAsyncTask extends AsyncTask<String, Void, List<Friend>>{
        
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

        @Override
        protected void onPostExecute(final List<Friend> friends){
            Log.d(TAG, "onPostExecute: called");

            if(friends.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.no_friends_in_course);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
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
