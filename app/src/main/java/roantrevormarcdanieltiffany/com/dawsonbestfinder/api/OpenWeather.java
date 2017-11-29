package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by sirMerr on 2017-11-28.
 */

public class OpenWeather {
    private static final String TAG = OpenWeather.class.getSimpleName();

    private static final String OPEN_WEATHER_UVI_URL = "http://api.openweathermap.org/data/2.5/uvi?appid=8116230ada270186d54714add001180f";
    // The format we want our API to return
    private static final String FORMAT = "json";
    // If there is error openweather api will have this in json
    private static final String ERROR_MESSAGE = "cod";

    // Params to pass to query
    static final String LAT_PARAM = "lat";
    static final String LON_PARAM = "lon";

    // Response keys
    static final String VALUE_KEY = "value";

    private Context context;

    /**
     * Instantiate class that will handle OpenWeather API calls
     *
     * @param context
     */
    public OpenWeather(Context context) {
        Log.d(TAG, "called OpenWeather()");
        this.context = context;
    }

    /**
     * Builds the URL request for the open weather api
     *
     * @param lat
     *      latitude of location
     * @param lon
     *      longitude of location
     * @return The URL used to query the weather api
     */
    public static URL buildUrl(String lat, String lon) {
        Log.d(TAG, "called buildUrl()");
        Uri builtUri = Uri.parse(OPEN_WEATHER_UVI_URL).buildUpon()
                .appendQueryParameter(LAT_PARAM, lat)
                .appendQueryParameter(LON_PARAM, lon)
                .build();

        try {
            URL url = new URL(builtUri.toString());
            Log.d(TAG, "Built URI" + url);
            return url;
        } catch (MalformedURLException err) {
            Log.d(TAG, err.getMessage());
            return null;
        }
    }

    /**
     * Returns entire result from HTTP response
     *
     * @param url
     *      The URL to fetch
     * @return The contents of the HTTP response
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(TAG, "called getResponseFromHttpUrl()");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // try-with-resources ensures the resources will be closed
        try (
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in)
        ) {
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            return hasInput? scanner.next() : null;
        }
    }

    /**
     * Parses JSON and returns the value for the uvi
     * (ultraviolet index)
     *
     * @param context
     * @param jsonResponse
     *      JSON response from api
     * @return double UVI value
     * @throws JSONException
     */
    public static String[] getUviValueFromJSON(Context context, String jsonResponse) throws JSONException{
        JSONObject uviJSON = new JSONObject(jsonResponse);

        if (uviJSON.has(ERROR_MESSAGE)) {
            int errorCode = uviJSON.getInt(ERROR_MESSAGE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    Log.e(TAG, "Could not find location");
                    break;
                default:
                    Log.e(TAG, "Something is wrong on their side");
                    break;
            }
        }

        return new String[] {uviJSON.getString(VALUE_KEY)};
    }
}
