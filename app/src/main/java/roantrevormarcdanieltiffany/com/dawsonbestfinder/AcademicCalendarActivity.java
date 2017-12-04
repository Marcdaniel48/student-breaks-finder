package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * Academic Calendar activity. Has radio buttons for
 * the season, an edit text for the year, and a
 * submit button
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class AcademicCalendarViewActivity extends Activity {
    private final static String TAG = "FRAG-CALENDARACTIVITY";

    private WebView webview;
    private RadioGroup rGroup;
    private EditText yearText;

    String url = "https://www.dawsoncollege.qc.ca/registrar/";
    String fullUrl = "https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/";

    /**
     * Will load a new url with the user input
     */
    public void onSubmit() {
        int rId = rGroup.getCheckedRadioButtonId();
        View radioButton = rGroup.findViewById(rId);
        int idx = rGroup.indexOfChild(radioButton);

        RadioButton r = (RadioButton) rGroup.getChildAt(idx);
        String text = r.getText().toString();

        String year = yearText.getText().toString();

        if(text == "summer") {
            webview.loadUrl(url+text+"-"+year+"/");
        } else {
            webview.loadUrl(url+text+"-"+year+"-day-division/");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate()");

        webview = findViewById(R.id.webview);
        rGroup = findViewById(R.id.seasonGroup);
        yearText = findViewById(R.id.yearText);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.loadUrl(fullUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }
}
