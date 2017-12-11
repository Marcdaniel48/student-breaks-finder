package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.QueryParam;

/**
 * Utility methods used for network operations for our api calls.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

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
        Log.d(TAG, "URL: " + url);
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
     * Builds URL request for the open weather api
     *
     * @return URL used to query the weather api
     */
    public static URL buildUrl(String apiUrl, List<QueryParam> params) {
        Log.d(TAG, "called buildTempUrl()");

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .encodedAuthority(apiUrl);

        for (QueryParam qp: params) {
            builder.appendQueryParameter(qp.getKey(), qp.getValue());
        }

        Uri builtUri = builder.build();

        try {
            URL url = new URL(builtUri.toString());
            Log.d(TAG, "Built URI: " + url);
            return url;
        } catch (MalformedURLException err) {
            Log.d(TAG, err.getMessage());
            return null;
        }
    }
}
