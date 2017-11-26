package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mrtvor on 2017-11-26.
 */

public class TemperatureFetcher {

    private static String weatherUrl = "api.openweathermap.org/data/2.5/weather?";
    GPSTracker userGps;

    public String getTemperatureData() {
        double latitude = userGps.getLatitude();
        double longitude = userGps.getLongitude();
        String key = "9b2d274f8b8845c5f9596502a83fb343";

        HttpURLConnection conn = null;
        InputStream stream = null;

        try {
            conn = (HttpURLConnection) (new URL(weatherUrl+"lat="+latitude+"&lon="+longitude+"&APPID="+key)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            StringBuffer buff = new StringBuffer();
            stream = conn.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while((line = buffer.readLine()) != null)
                buff.append(line + "rn");

            stream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
