package roantrevormarcdanieltiffany.com.dawsonbestfinder.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Forecast;


/**
 * Methods that are used to communicate with the openweather api and
 * handle the JSON data response
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class OpenWeather {
    private static final String TAG = OpenWeather.class.getSimpleName();

    // API app id
    public static final String APP_ID = "8116230ada270186d54714add001180f";

    // Open Weather API url
    public static final String OPEN_WEATHER_TEMP_URL = "http://api.openweathermap.org/data/2.5/weather?appid=8116230ada270186d54714add001180f";
    public static final String OPEN_WEATHER_UVI_URL = "api.openweathermap.org/data/2.5/uvi";
    public static final String OPEN_WEATHER_FORECAST_URL = "api.openweathermap.org/data/2.5/forecast";

    // APP ID query param
    public static final String APP_ID_PARAM = "appid";

    // If there is error openweather api will have this in json
    private static final String ERROR_MESSAGE = "cod";

    // Params to pass to uvi query
    public static final String LAT_PARAM = "lat";
    public static final String LON_PARAM = "lon";

    // Params to pass to forecast query
    public static final String Q_PARAM = "q";

    // Response keys
    private static final String VALUE_KEY = "value";
    private static final String MAIN_KEY = "main";
    private static final String TEMP_KEY = "temp_min";
    private static final String TEMP_MAX_KEY = "temp_max";
    private static final String TEMP_MIN_KEY = "temp_min";
    private static final String PRESSURE_KEY = "pressure";
    private static final String SEA_LEVEL_KEY = "sea_level";
    private static final String GRND_LEVEL_KEY = "grnd_level";
    private static final String HUMIDITY_KEY = "humidity";
    private static final String TEMP_KF_KEY = "temp_kf";
    private static final String WEATHER_KEY = "weather";
    private static final String WEATHER_ID_KEY = "id";
    private static final String WEATHER_DESCRIPTION_KEY = "description";
    private static final String WEATHER_ICON_KEY = "icon";
    private static final String CLOUDS_KEY = "clouds";
    private static final String CLOUDS_ALL_KEY = "all";
    private static final String WIND_KEY = "wind";
    private static final String WIND_SPEED_KEY = "speed";
    private static final String WIND_DEG_KEY = "deg";
    private static final String RAIN_KEY = "rain";
    private static final String H3_KEY = "3h";
    private static final String SNOW_KEY = "snow";
    private static final String LIST_KEY = "list";
    private static final String DT_TXT_KEY = "dt_txt";

    /**
     * Instantiate class that will handle OpenWeather API calls
     */
    public OpenWeather() {
        Log.d(TAG, "called OpenWeather()");
    }

    /**
     * Builds URL request for the open weather api
     *
     * @param lat user latitude
     * @param lon user longitude
     * @return URL used to query the weather api
     */
    public static URL buildTempUrl(String lat, String lon) {
        Log.d(TAG, "called buildTempUrl()");
        Uri builtUri = Uri.parse(OPEN_WEATHER_TEMP_URL).buildUpon()
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
     * Parses JSON and returns the value for the uvi
     * (ultraviolet index)
     *
     * @param jsonResponse
     *      JSON response from api
     * @return double UVI value
     * @throws JSONException
     */
    public static String getUviValueFromJSON(String jsonResponse) throws JSONException{
        Log.d(TAG, "called getUviValueFromJSON()");
        JSONObject uviJSON = new JSONObject(jsonResponse);

        if (uviJSON.has(ERROR_MESSAGE)) {
            int errorCode = uviJSON.getInt(ERROR_MESSAGE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    Log.e(TAG, "Successful Call to UVI API");
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    Log.e(TAG, "Could not find location");
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    Log.e(TAG, "Wrong APP ID");
                    break;
                default:
                    Log.e(TAG, "Something is wrong on their side");
                    break;
            }
        }

        return uviJSON.getString(VALUE_KEY);
    }

    /**
     * Parses JSON and returns a list of Forecasts
     *
     * @param jsonResponse
     * @return List<Forecast>
     * @throws JSONException
     */
    public static List<Forecast> getForecastFromJSON(String jsonResponse) throws JSONException {
        Log.d(TAG, "called getForecastFromJSON()");

        Log.d(TAG, "response: " + jsonResponse);
        // Get the hour
        Calendar rightNow = Calendar.getInstance();
        int hourParsed = rightNow.get(Calendar.HOUR_OF_DAY);
        int modulus3 = hourParsed % 3;

        if (modulus3 != 0 ) {
            // Make sure it's every 3 hours (Ex: 10am -> 9am, 11am -> 12am)
            hourParsed-= modulus3 == 1 ? 1 : -1;
        }

        String hour;

        // Make same format as DT_TXT
        if (hourParsed == 24) {
            hour = "00:00:00";
        } else {
            hour = hourParsed + ":00:00";
        }

        Log.d(TAG, "Hour of the day: " + hour);

        JSONObject json = new JSONObject(jsonResponse);

        if (json.has(ERROR_MESSAGE)) {
            int errorCode = json.getInt(ERROR_MESSAGE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    Log.e(TAG, "Successful Call to Forecast API");
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    Log.e(TAG, "Could not find location");
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    Log.e(TAG, "Wrong APP ID");
                    break;
                default:
                    Log.e(TAG, "Something is wrong on their side");
                    break;
            }
        }

        JSONArray forecastArr = json.getJSONArray(LIST_KEY);
        List<Forecast> forecasts = new ArrayList<>();

        for (int i = 0; i < forecastArr.length(); i++) {
            // Get JSON object representing the day
            JSONObject dayForecast = forecastArr.getJSONObject(i);

            // Only take objects whose hour is the one we want
            if (dayForecast.getString(DT_TXT_KEY).matches("(.*)"+hour+"$")) {
                Log.d(TAG, "Matches hour: " + dayForecast);
                Forecast forecast = getForecastFromDay(dayForecast);
                forecasts.add(forecast);
            }
        }

        return forecasts;
    }

    /**
     * Parses JSON and gets the current temperature
     *
     * @param jsonResponse JSON response from api
     * @return double temp value
     * @throws JSONException
     */
    public static String getTempValueFromJSON(String jsonResponse) throws JSONException {
        JSONObject tempJSON = new JSONObject(jsonResponse);

        if(tempJSON.has(ERROR_MESSAGE)) {
            int errorCode = tempJSON.getInt(ERROR_MESSAGE);

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

        JSONObject main = tempJSON.getJSONObject("main");
        String temp = main.getString("temp");
        Log.d(TAG, "TEMPERATURE: " + temp);

        return temp;
    }

    /**
     * Parses JSON and makes a Forecast object
     *
     * @param dayForecast
     * @return
     * @throws JSONException
     */
    private static Forecast getForecastFromDay(JSONObject dayForecast) throws JSONException {
        JSONObject mainObj = dayForecast.getJSONObject(MAIN_KEY);
        JSONObject weatherObj = dayForecast.getJSONArray(WEATHER_KEY).getJSONObject(0);

        Forecast forecast = new Forecast();

        Log.d(TAG, "DT_TXT_KEY: " + dayForecast.getString(DT_TXT_KEY));
        forecast.setDay(dayForecast.getString(DT_TXT_KEY));

        if (mainObj.has(TEMP_KEY)) {
            forecast.setTemperature(mainObj.getDouble(TEMP_KEY));
        }

        if (mainObj.has(TEMP_MIN_KEY)) {
            forecast.setTemperatureMin(mainObj.getDouble(TEMP_MIN_KEY));
        }

        if (mainObj.has(TEMP_MAX_KEY)) {
            forecast.setTemperatureMax(mainObj.getDouble(TEMP_MAX_KEY));
        }

        if (mainObj.has(PRESSURE_KEY)) {
            forecast.setPressure(mainObj.getDouble(PRESSURE_KEY));
        }

        if (mainObj.has(SEA_LEVEL_KEY)) {
            forecast.setSeaLevel(mainObj.getDouble(SEA_LEVEL_KEY));
        }

        if (mainObj.has(GRND_LEVEL_KEY)) {
            forecast.setGrndLevel(mainObj.getDouble(GRND_LEVEL_KEY));
        }

        if (mainObj.has(HUMIDITY_KEY)) {
            forecast.setHumidity(mainObj.getDouble(HUMIDITY_KEY));
        }

        if (mainObj.has(TEMP_KF_KEY)) {
            forecast.setTemperatureKf(mainObj.getDouble(TEMP_KF_KEY));
        }

        if (weatherObj.has(WEATHER_ID_KEY)) {
            forecast.setWeatherId(weatherObj.getInt(WEATHER_ID_KEY));
        }

        if (weatherObj.has(MAIN_KEY)) {
            forecast.setWeatherMain(weatherObj.getString(MAIN_KEY));
        }

        if (weatherObj.has(WEATHER_DESCRIPTION_KEY)) {
            forecast.setWeatherDescription(weatherObj.getString(WEATHER_DESCRIPTION_KEY));
        }

        if (weatherObj.has(WEATHER_DESCRIPTION_KEY)) {
            forecast.setWeatherDescription(weatherObj.getString(WEATHER_DESCRIPTION_KEY));
        }

        if (weatherObj.has(WEATHER_DESCRIPTION_KEY)) {
            forecast.setWeatherDescription(weatherObj.getString(WEATHER_DESCRIPTION_KEY));
        }

        if (weatherObj.has(WEATHER_ICON_KEY)) {
            forecast.setWeatherIcon(weatherObj.getString(WEATHER_ICON_KEY));
        }

        if (dayForecast.has(CLOUDS_KEY) && dayForecast.getJSONObject(CLOUDS_KEY).has(CLOUDS_ALL_KEY)) {
            forecast.setCloudsAllPercentage(dayForecast.getJSONObject(CLOUDS_KEY).getDouble(CLOUDS_ALL_KEY));
        }

        if (dayForecast.has(WIND_KEY)) {
            JSONObject windObj = dayForecast.getJSONObject(WIND_KEY);
            if (windObj.has(WIND_DEG_KEY))
                forecast.setWindDeg(windObj.getDouble(WIND_DEG_KEY));
            if (windObj.has(WIND_SPEED_KEY))
                forecast.setWindSpeed(windObj.getDouble(WIND_SPEED_KEY));
        }

        if (dayForecast.has(RAIN_KEY) && dayForecast.getJSONObject(RAIN_KEY).has(H3_KEY)) {
            forecast.setRainMM3h( dayForecast.getJSONObject(RAIN_KEY).getDouble(H3_KEY));
        }

        if (dayForecast.has(SNOW_KEY) && dayForecast.getJSONObject(SNOW_KEY).has(H3_KEY)) {
            forecast.setSnowVol3h( dayForecast.getJSONObject(SNOW_KEY).getDouble(H3_KEY));
        }

        return forecast;
    }
}
