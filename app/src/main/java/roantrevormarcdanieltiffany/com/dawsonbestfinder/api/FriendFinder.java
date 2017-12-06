package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Friend;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.FriendLocation;

/**
 * API class that's used to make Friend Finder API calls.
 */
public class FriendFinder
{
    private static final String TAG = FriendFinder.class.getSimpleName();

    private static final String FIRSTNAME_KEY = "firstname";
    private static final String LASTNAME_KEY = "lastname";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static final String COURSE_KEY = "course";
    private static final String SECTION_KEY = "section";

    private static final String FRIEND_EMAIL_KEY = "friendemail";
    private static final String DAY_KEY = "day";
    private static final String TIME_KEY = "time";

    private static final String FIND_FRIENDS_URL = "http://dawsonbestfinder.herokuapp.com/api/api/allfriends?";
    private static final String FRIEND_LOCATION_URL = "http://dawsonbestfinder.herokuapp.com/api/api/whereisfriend?";

    /**
     * Instantiates FriendFinder
     */
    public FriendFinder()
    {
        Log.d(TAG, "Instance of FriendFinder initiated.");
    }

    /**
     * Takes in a JSON Response in the form of a String and parses it to return a list of Friends.
     *
     * @param jsonResponse
     * @return
     * @throws JSONException
     */
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

    /**
     * Takes in a JSON response in the form of a String and parses it to return a FriendLocation.
     *
     * @param jsonResponse
     * @return
     * @throws JSONException
     */
    public static FriendLocation getFriendLocationFromJSON(String jsonResponse) throws JSONException
    {
        Log.d(TAG, "Called getFriendLocationFromJSON(String jsonResponse)");

        JSONArray friendsJSONArray = new JSONArray(jsonResponse);
        JSONObject jsonObject = friendsJSONArray.getJSONObject(0);
        FriendLocation location = parseJSONToFriendLocation(jsonObject);

        return location;
    }

    /**
     * Takes in a JSONObject and parses it into a Friend object.
     *
     * @param friendJSON
     * @return
     * @throws JSONException
     */
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

    /**
     * Takes in a JSONObject and parses it into a FriendLocation object.
     * @param locationJSON
     * @return
     * @throws JSONException
     */
    private static FriendLocation parseJSONToFriendLocation(JSONObject locationJSON) throws JSONException
    {
        Log.d(TAG, "Called parseJSONToFriendLocation(JSONObject locationJSON)");

        FriendLocation location = new FriendLocation();

        JSONObject jsonObject = locationJSON;

        if(jsonObject.has(COURSE_KEY) && jsonObject.has(SECTION_KEY))
        {
            location.setCourse(jsonObject.getString(COURSE_KEY));
            location.setSection(jsonObject.getString(SECTION_KEY));
        }

        return location;
    }

    /**
     * Takes in an email and password Strings and builds an API call URL that's supposed to return a JSON response listing the user's friends.
     * @param email
     * @param password
     * @return
     * @throws MalformedURLException
     */
    public static URL buildFindFriendsURL(String email, String password) throws MalformedURLException
    {
        Log.d(TAG, "Called buildFindFriendsURL(String email, String password)");

        Uri uri = Uri.parse(FIND_FRIENDS_URL).buildUpon().appendQueryParameter(EMAIL_KEY, email).appendQueryParameter(PASSWORD_KEY, password).build();
        URL url = new URL(uri.toString());
        return url;
    }

    /**
     * Takes in a number of String parameters and uses it to build an API call URL to return a JSON response describing the location of a user's friend.
     *
     * @param email
     * @param password
     * @param friendEmail
     * @param day
     * @param time
     * @return
     * @throws MalformedURLException
     */
    public static URL buildFriendLocationURL(String email, String password, String friendEmail, String day, String time) throws MalformedURLException
    {
        Log.d(TAG, "buildFriendLocationURL(String email, String password, String friendEmail, int day, int time)");

        Uri uri = Uri.parse(FRIEND_LOCATION_URL).buildUpon().appendQueryParameter(EMAIL_KEY, email).appendQueryParameter(PASSWORD_KEY, password)
        .appendQueryParameter(FRIEND_EMAIL_KEY, friendEmail).appendQueryParameter(DAY_KEY, day).appendQueryParameter(TIME_KEY, time).build();

        URL url = new URL(uri.toString());
        return url;
    }


}
