package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Forecast;

/**
 * Activity which has a widget to input the city & use a spinner
 * for the ISO 3166 country codes, default to Montreal,CA.
 * Have a button to display the UV index and the 5 day forecast
 * This can be in the same Activity/Fragment or launch a new one
 *
 * {@see https://developer.android.com/guide/topics/providers/calendar-provider.html}
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class WeatherActivity extends MenuActivity {
    private static final String TAG = WeatherActivity.class.getSimpleName();

    private TextView tvTestData;
    private ListView lvForecast;
    private LinearLayout llWeather;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvTestData = findViewById(R.id.tvTestData);
        lvForecast = findViewById(R.id.lvForecast);
        llWeather = findViewById(R.id.llWeather);

        // Only show weather on button click
        llWeather.setVisibility(LinearLayout.GONE);
    }

    /**
     * This method will get the user's location to get
     * a ultraviolet index from the openweather api in the
     * background
     */
    private void loadUVIData() {
        Log.d(TAG, "called loadUVIData()");
        // @todo Replace lat/lon with non-fake data
        double lat = 37.75;
        double lon = -122.37;

        new OpenWeatherTask().execute(String.valueOf(lat),String.valueOf(lon));
        new OpenWeatherForecast().execute("Montreal", "CA");
    }

    public void clickForecast(View view) {
        loadUVIData();
    }

    /**
     * Class that extends AsyncTask to perform network requests
     */
    public class OpenWeatherTask extends AsyncTask<String, Void, String[]> {
        private final String TAG = OpenWeatherTask.class.getSimpleName();

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
                return OpenWeather.getUviValueFromJSON(WeatherActivity.this, json);
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

            for (String data: strings) {
                Log.d(TAG, data);
            }

            llWeather.setVisibility(LinearLayout.VISIBLE);
            tvTestData.setText(strings[0]);
        }
    }

    /**
     * Class that extends AsyncTask to perform network requests
     */
    public class OpenWeatherForecast extends AsyncTask<String, Void, List<Forecast>> {
        private final String TAG = OpenWeatherForecast.class.getSimpleName();

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
        protected List<Forecast> doInBackground(String... strings) {
            Log.d(TAG, "called doInBackground()");
            if (strings.length == 0) {
                Log.d(TAG, "No params");
                return new ArrayList<>();
            }

            String cityName = strings[0];
            String countryCode = strings[1];

            URL url = OpenWeather.buildForecastUrl(cityName, countryCode);

            try {
                Log.d(TAG, "Url!!: " + url);
                String json = OpenWeather.getResponseFromHttpUrl(url);
                return OpenWeather.getForecastFromJSON(json);
            } catch (Exception err) {
                Log.e(TAG, err.getLocalizedMessage());
                return new ArrayList<>();
            }
        }

        /**
         * Overrides {@code onPostExecute} to display results
         *
         * @param forecasts
         */
        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            Log.d(TAG, "called onPostExecute()");

            Log.d(TAG, "Forecast list: " + forecasts);

            ArrayAdapter<Forecast> adapter = new ArrayAdapter<>(WeatherActivity.this, android.R.layout.simple_list_item_1, forecasts);

            lvForecast.setAdapter(adapter);
        }
    }
}
