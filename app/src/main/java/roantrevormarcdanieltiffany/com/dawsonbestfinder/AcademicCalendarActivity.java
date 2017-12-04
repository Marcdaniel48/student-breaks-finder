package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
public class AcademicCalendarActivity extends Activity {
    private final static String TAG = AcademicCalendarActivity.class.getSimpleName();

    private WebView webview;
    private RadioGroup rGroup;
    private EditText yearText;

    private final String url = "https://www.dawsoncollege.qc.ca/registrar/";
    private final String fullUrl = "https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/";

    /**
     * Will load a new url with the user input
     */
    public void onSubmit(View v) {
        int rId = rGroup.getCheckedRadioButtonId();
        View radioButton = rGroup.findViewById(rId);
        int idx = rGroup.indexOfChild(radioButton);

        RadioButton r = (RadioButton) rGroup.getChildAt(idx);
        String text = r.getText().toString();

        String year = yearText.getText().toString();

        webview.loadUrl(url+text+"-"+year+"-day-division/");
    }

    /**
     * When invoked will display the widgets,
     * and display the webview page
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);
        Log.d(TAG, "onCreate()");

        webview = findViewById(R.id.webview);
        rGroup = findViewById(R.id.seasonGroup);
        yearText = findViewById(R.id.yearText);

        webview.loadUrl(fullUrl);
    }
}
