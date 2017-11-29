package roantrevormarcdanieltiffany.com.dawsonbestfinder.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.TeacherContactActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.WeatherActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;

/**
 * Created by sirMerr on 2017-11-28.
 */

public class OpenWeatherTask extends AsyncTask<String, Void, String[]> {
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private WeatherActivity weatherActivity;
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String[] doInBackground(String... strings) {
        Log.d(TAG, "called doInBackground()");
        if (strings.length == 0) {
            Log.d(TAG, "No params");
            return new String[]{};
        }

        String lat = strings[0];
        String lon = strings[1];

        URL url = OpenWeather.buildUrl(lat, lon);

        try {
            String json = OpenWeather.getResponseFromHttpUrl(url);
            return OpenWeather.getUviValueFromJSON(weatherActivity, json);
        } catch (Exception err) {
            Log.e(TAG, err.getLocalizedMessage());
            return new String[]{};
        }
    }

    /**
     * Overrides {@code onPostExecute} to display results
     *
     * @param strings
     */
    @Override
    protected void onPostExecute(String[] strings) {
        Log.d(TAG, "called onPostExecute()");
//            super.onPostExecute(strings);
    }
}
