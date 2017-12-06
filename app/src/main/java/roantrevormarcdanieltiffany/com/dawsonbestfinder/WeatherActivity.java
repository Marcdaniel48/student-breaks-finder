package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Forecast;

/**
 * Activity which has a widget to input the city & uses a spinner
 * for the ISO 3166 country codes (default is Montreal,CA) .
 * It has a button handler to display the UV index and the 5 day forecast
 * which displays in the same activity
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

    private TextView tvTestData, tvForecast;
    private ListView lvForecast;
    private LinearLayout llWeather;
    private Spinner spinner;
    private EditText etCityName;
    private final String LAT_KEY = "lat";
    private final String LON_KEY = "lon";
    private final String FORECAST_CLICKED = "forecastClicked";
    private final String FORECASTS_LIST = "forecastsList";
    private final String UVI = "uvi";
    private ArrayList<String> loadedForecasts = new ArrayList<>();
    private Float lat, lon;


    /**
     * Overrides {@code onCreate} to prep the layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvTestData = findViewById(R.id.tvUVI);
        tvForecast = findViewById(R.id.tvForecast);
        lvForecast = findViewById(R.id.lvForecast);
        llWeather = findViewById(R.id.llWeather);
        spinner = findViewById(R.id.spinner);
        etCityName = findViewById(R.id.etCityName);

        // Get lat/lon
        getSharedPreferences();

        // Prep spinner
        setISOCodes();

        // Only show weather on button click
        llWeather.setVisibility(LinearLayout.GONE);

        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(FORECAST_CLICKED) == LinearLayout.VISIBLE) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(WeatherActivity.this, android.R.layout.simple_list_item_1, savedInstanceState.getStringArrayList(FORECASTS_LIST));

                lvForecast.setAdapter(adapter);
                llWeather.setVisibility(savedInstanceState.getInt(FORECAST_CLICKED, LinearLayout.GONE));
                tvTestData.setText(savedInstanceState.getString(UVI, ""));
            }
        }

    }

    /**
     * Get Lat and Lon from shared preferences
     */
    private void getSharedPreferences() {
        Log.d(TAG, "called getSharedPreferences()");
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.contains(LAT_KEY) && prefs.contains(LON_KEY)) {
            // Should never give default value
            lat = prefs.getFloat(LAT_KEY, 0);
            lon = prefs.getFloat(LON_KEY, 0);
        }
    }

    /**
     * Set ISO codes in the spinner and default to CA
     */
    private void setISOCodes() {
        Log.d(TAG, "called setISOCodes()");
        String[] locales = Locale.getISOCountries();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locales);

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(getString(R.string.canada_code)));
    }
    /**
     * This method will get the user's location to get
     * a ultraviolet index from the openweather api in the
     * background
     */
    private void loadOpenWeatherData() {
        Log.d(TAG, "called loadOpenWeatherData()");
        if (etCityName.getText().toString().trim().equals("")) {
            Log.d(TAG, "No city name specified");
            etCityName.setError("The city name is required!");
            return;
        }

        if (lat == null || lon == null) {
            Toast.makeText(WeatherActivity.this, "No latitude/longitude!",
                    Toast.LENGTH_LONG).show();
        } else {
            new OpenWeatherUVITask().execute(String.valueOf(lat),String.valueOf(lon));
        }

        new OpenWeatherForecastTask().execute(etCityName.getText().toString(), spinner.getSelectedItem().toString());

        llWeather.setVisibility(LinearLayout.VISIBLE);
    }

    /**
     * Forecast button handler
     *
     * @param view
     */
    public void clickForecast(View view) {
        Log.d(TAG, "called clickForecast()");
        loadOpenWeatherData();
    }

    /**
     * Save uvi and forecast to state bundle
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "called onSaveInstanceState()");
        super.onSaveInstanceState(outState);

        outState.putInt(FORECAST_CLICKED, llWeather.getVisibility());
        outState.putStringArrayList(FORECASTS_LIST, loadedForecasts);
        outState.putString(UVI, tvTestData.getText().toString());
    }

    /**
     * Class that extends AsyncTask to perform network requests
     */
    public class OpenWeatherUVITask extends AsyncTask<String, Void, String[]> {
        private final String TAG = OpenWeatherUVITask.class.getSimpleName();

        /**
         * Retrieves the UVI data from the api.
         *
         * Overrides {@code doInBackground} to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
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
                return OpenWeather.getUviValueFromJSON(json);
            } catch (Exception err) {
                Log.e(TAG, err.getLocalizedMessage());
                return new String[]{};
            }
        }

        /**
         * Displays UVI data inside layout
         *
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
    public class OpenWeatherForecastTask extends AsyncTask<String, Void, List<Forecast>> {
        private final String TAG = OpenWeatherForecastTask.class.getSimpleName();

        /**
         * Find forecasts for the 3h interval closest to the current hour
         *
         * Overrides {@code doInBackground} to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         *
         * @param strings The parameters of the task.
         * @return List of {@code Forecasts}
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
         * or a toast if no data was found
         *
         * @param forecasts
         */
        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            Log.d(TAG, "called onPostExecute()");

            loadedForecasts = new ArrayList<>();

            for(Forecast forecast: forecasts) {
                loadedForecasts.add(forecast.toString());
            }

            if (forecasts.isEmpty()) {
                Log.d(TAG, "forecasts.isEmpty");
                Toast.makeText(WeatherActivity.this, "City name/Country code Invalid!",
                        Toast.LENGTH_LONG).show();
                lvForecast.setVisibility(ListView.GONE);
                return;
            }
            ArrayAdapter<Forecast> adapter = new ArrayAdapter<>(WeatherActivity.this, android.R.layout.simple_list_item_1, forecasts);

            lvForecast.setAdapter(adapter);
        }
    }
}
