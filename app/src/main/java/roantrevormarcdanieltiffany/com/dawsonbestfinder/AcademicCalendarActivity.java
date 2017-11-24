package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by mrtvor on 2017-11-24.
 */

public class AcademicCalendarActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academiccalendar);

        webView = findViewById(R.id.webview);
        String url = "https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/";
        webView.loadUrl(url);

    }
}
