package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static final String FIRSTNAME_KEY = "firstname";
    public static final String LASTNAME_KEY = "lastname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    private static final String COURSE_KEY = "course";
    private static final String SECTION_KEY = "section";

    public static final String FRIEND_EMAIL_KEY = "friendemail";
    public static final String DAY_KEY = "day";
    public static final String TIME_KEY = "time";

    // For API calling.
    // Find all friends of the current user.
    public static final String FIND_FRIENDS_URL = "dawsonbestfinder.herokuapp.com/api/api/allfriends";
    // Find the current location of one of the current user's friends.
    public static final String FRIEND_LOCATION_URL = "dawsonbestfinder.herokuapp.com/api/api/whereisfriend";
    
    /**
     * Takes in a JSON Response in the form of a String and parses it to return a list of Friends.
     *
     * @param jsonResponse
     * @return A list of the user's friends.
     * @throws JSONException
     */
    public static List<Friend> getFriendsFromJSON(String jsonResponse) throws JSONException
    {
        Log.d(TAG, "Called getFriendsFromJSON(String jsonResponse)");

        List<Friend> friendsList = new ArrayList<>();

        JSONArray friendsJSONArray = new JSONArray(jsonResponse);

        // Logs the JSON Array of Friends
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
     * @return The location of the user's requested friend
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
     * @return A Friend object parsed from a JSON object
     * @throws JSONException
     */
    private static Friend parseJSONToFriend(JSONObject friendJSON) throws JSONException
    {
        Log.d(TAG, "Called parseJSONToFriend(JSONObject friendJSON)");

        Friend friend = new Friend();

        JSONObject jsonObject = friendJSON;

        // The JSON should have a first name, last name, and email
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
     * @return The location of a requested friend parsed from a JSON object.
     * @throws JSONException
     */
    private static FriendLocation parseJSONToFriendLocation(JSONObject locationJSON) throws JSONException
    {
        Log.d(TAG, "Called parseJSONToFriendLocation(JSONObject locationJSON)");

        FriendLocation location = new FriendLocation();

        JSONObject jsonObject = locationJSON;

        // The JSON should have a course and section.
        if(jsonObject.has(COURSE_KEY) && jsonObject.has(SECTION_KEY))
        {
            location.setCourse(jsonObject.getString(COURSE_KEY));
            location.setSection(jsonObject.getString(SECTION_KEY));
        }

        return location;
    }
}
