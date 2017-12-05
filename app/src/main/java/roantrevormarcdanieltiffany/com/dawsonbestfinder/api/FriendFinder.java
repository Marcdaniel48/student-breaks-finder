package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;

public class FriendFinder
{
    private static final String TAG = FriendFinder.class.getSimpleName();
    private static final String FIRSTNAME_KEY = "firstname";
    private static final String LASTNAME_KEY = "lastname";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static final String FIND_FRIENDS_URL = "http://dawsonbestfinder.herokuapp.com/api/api/allfriends?";

    public FriendFinder()
    {
        Log.d(TAG, "Instance of FriendFinder initiated.");
    }

    public static List<Friend> getFriendsFromJSON(String jsonResponse) throws JSONException
    {
        Log.d(TAG, "Called getFriendsFromJSON(String jsonResponse)");

        List<Friend> friendsList = new ArrayList<>();

        JSONArray friendsJSONArray = new JSONArray(jsonResponse);
        Log.d(TAG, "friendJSONArray: " + friendsJSONArray.toString());
        for (int i = 0; i < friendsJSONArray.length(); i++)
        {
            JSONObject jsonObject = friendsJSONArray.getJSONObject(i);

            Friend friend = parseJSONToFriend(jsonObject);
            friendsList.add(friend);
        }

        return friendsList;
    }

    private static Friend parseJSONToFriend(JSONObject friendJSON) throws JSONException
    {
        Log.d(TAG, "Called parseJSONToFriend(JSONObject friendJSON)");

        Friend friend = new Friend();

        JSONObject jsonObject = friendJSON;

        if(jsonObject.has(FIRSTNAME_KEY) && jsonObject.has(LASTNAME_KEY) && jsonObject.has(EMAIL_KEY))
        {
            friend.setFirstname(jsonObject.getString(FIRSTNAME_KEY));
            friend.setLastname(jsonObject.getString(LASTNAME_KEY));
            friend.setEmail(jsonObject.getString(EMAIL_KEY));
        }

        return friend;
    }

    public static URL buildFindFriendsURL(String email, String password) throws MalformedURLException
    {
        Log.d(TAG, "Called buildFindFriendsURL(String email, String password)");

        Uri uri = Uri.parse(FIND_FRIENDS_URL).buildUpon().appendQueryParameter(EMAIL_KEY, email).appendQueryParameter(PASSWORD_KEY, password).build();
        System.out.println(uri.toString());
        URL url = new URL(uri.toString());
        return url;
    }


}
