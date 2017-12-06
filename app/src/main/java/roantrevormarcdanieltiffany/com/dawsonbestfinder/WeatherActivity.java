package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.os.AsyncTask;
import android.os.Bundle;
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

import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.NetworkUtils;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.api.OpenWeather;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Forecast;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.QueryParam;

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

        // Prep spinner
        setISOCodes();

        // Only show weather on button click
        llWeather.setVisibility(LinearLayout.GONE);
    }

    /**
     * Set ISO codes in the spinner and default to CA
     */
    private void setISOCodes() {
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

        // @todo Replace lat/lon with non-fake data
        double lat = 37.75;
        double lon = -122.37;

        new OpenWeatherUVITask().execute(String.valueOf(lat),String.valueOf(lon));
        new OpenWeatherForecastTask().execute(etCityName.getText().toString(), spinner.getSelectedItem().toString());
    }

    /**
     * Forecast button handler
     *
     * @param view
     */
    public void clickForecast(View view) {
        loadOpenWeatherData();
    }

    /**
     * Class that extends AsyncTask to perform network requests
     */
    public class OpenWeatherUVITask extends AsyncTask<String, Void, String> {
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
        protected String doInBackground(String... strings) {
            Log.d(TAG, "called doInBackground()");
            if (strings.length == 0) {
                Log.d(TAG, "No params");
                return "";
            }

            String lat = strings[0];
            String lon = strings[1];

            List<QueryParam> queryParams = new ArrayList<>();
            queryParams.add(new QueryParam(OpenWeather.LAT_PARAM, lat));
            queryParams.add(new QueryParam(OpenWeather.LON_PARAM, lon));

            URL url = NetworkUtils.buildUrl(OpenWeather.OPEN_WEATHER_UVI_URL, queryParams);

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                return OpenWeather.getUviValueFromJSON(json);
            } catch (Exception err) {
                Log.e(TAG, err.getLocalizedMessage());
                return "";
            }
        }

        /**
         * Displays UVI data inside layout
         *
         * Overrides {@code onPostExecute} to display results
         *
         * @param string
         */
        @Override
        protected void onPostExecute(String string) {
            Log.d(TAG, "called onPostExecute()");

            Log.d(TAG, "Data found: " + string);

            llWeather.setVisibility(LinearLayout.VISIBLE);
            tvTestData.setText(string);
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

            List<QueryParam> queryParams = new ArrayList<>();
            queryParams.add(new QueryParam(OpenWeather.Q_PARAM, cityName + "," + countryCode));
            URL url = NetworkUtils.buildUrl(OpenWeather.OPEN_WEATHER_FORECAST_URL, queryParams);

            try {
                Log.d(TAG, "Url!!: " + url);
                String json = NetworkUtils.getResponseFromHttpUrl(url);
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
