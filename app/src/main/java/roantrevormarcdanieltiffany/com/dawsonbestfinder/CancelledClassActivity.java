package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class CancelledClassActivity extends AppCompatActivity {

    private ListView lv;
    private final String TAG = "Canceled Class Activity";
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create call");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class);
        loadClasses();
    }

    private void loadClasses(){
        Log.d(TAG, "load classes called");
        String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";
        Log.d(TAG, "right before cman");
        ConnectivityManager cman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d(TAG, "right after cman");
        NetworkInfo neti = cman.getActiveNetworkInfo();
        if(neti != null && neti.isConnected()){
            new DownloadRSSThread().execute(url);
        }else{
            Log.d(TAG, "not connected?");
        }

    }

    private void HandleRSSString(String string){

    }

}




