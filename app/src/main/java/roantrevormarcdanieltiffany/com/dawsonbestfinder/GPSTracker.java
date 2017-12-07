package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * GPS Tracker Service, most of the code is from
 * the provided tutorial:
 *
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;

    private Location location;
    private double latitude;
    private double longitude;

    protected final String LAT = "lat";
    protected final String LON = "lon";
    public static final String TAG = GPSTracker.class.getSimpleName();
    public static final long MIN_DISTANCE_BW_UPDATES = 1000; // 1KM
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60; //1 minute

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        Log.d(TAG, "GPSTracker()");
        this.mContext = context;
        getLocation();
    }

    /**
     * Will attempt to retrieve the location,
     * first through gps, then through network
     * provider
     *
     * @return location
     */
    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            Log.d(TAG, "getLocation()");
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d(TAG, "Neither GPS nor Network is enabled");
            } else {
                this.canGetLocation = true;

                if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_BW_UPDATES, this);
                        Log.d(TAG, "Network");

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null) {
                            Log.d(TAG, "setting lat and long from network provider");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled) {
                    if(location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_BW_UPDATES, this);
                        Log.d(TAG, "GPS Enabled");

                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null) {
                                Log.d(TAG, "setting lat and long from gps");
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(LAT, (float) latitude);
            editor.putFloat(LON, (float) longitude);
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopUsingGPS();
        }

        return location;
    }

    /**
     * Stop locationManager from using gps
     */
    public void stopUsingGPS() {
        Log.d(TAG, "stopUsingGPS()");
        if(locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Boolean indicating whether location can
     * be retrieved successfully
     * @return boolean
     */
    public boolean canGetLocation() {
        Log.d(TAG, "canGetLocation()");
        return this.canGetLocation;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged()");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged()");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled()");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled()");
    }
}
