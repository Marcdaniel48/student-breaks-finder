package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mrtvor on 2017-11-26.
 */

public class TemperatureFetcher extends AsyncTask<String, Void, String[]> {

    private final static String TAG = TemperatureFetcher.class.getSimpleName();

    @Override
    protected String[] doInBackground(String... strings) {
        Log.d(TAG, "doInBackground()");

        if(strings.length <= 1 || strings.length > 2) {
            Log.d(TAG, "Invalid number of params passed");
            return null;
        }

        double latitude = Double.parseDouble(strings[0]);
        double longitude = Double.parseDouble(strings[1]);
        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?";

        String key = "9b2d274f8b8845c5f9596502a83fb343";

        Log.d(TAG, "LAT: " + latitude + ", LONG: " + longitude);

        HttpURLConnection conn ;
        InputStream stream;

        try {
            conn = (HttpURLConnection) (new URL(weatherUrl+"lat="+latitude+"&lon="+longitude+"&appid="+key)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            StringBuffer buff = new StringBuffer();
            stream = conn.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while((line = buffRead.readLine()) != null)
                buff.append(line + "rn");

            stream.close();
            conn.disconnect();

            Log.d(TAG, "STREAM PASSED");
            JSONObject obj = new JSONObject(buff.toString());
            JSONObject main = obj.getJSONObject("main");
            String temp = main.getString("temp");
            Log.d(TAG, "TEMPERATURE: " + temp);

            return new String[] {temp};
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[] {"Temperature unattainable"};
    }
}
