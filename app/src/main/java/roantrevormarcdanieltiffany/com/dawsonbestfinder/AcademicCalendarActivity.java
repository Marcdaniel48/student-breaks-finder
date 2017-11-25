package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

/**
 * Created by mrtvor on 2017-11-24.
 */

public class AcademicCalendarActivity extends WebViewFragment {

    private final static String TAG ="FRAG-WEBVIEIWFRAGMENT";
    String url = "https://www.dawsoncollege.qc.ca/registrar/";

    public static AcademicCalendarActivity newInstance(String season, String year) {
        Log.v(TAG, "Creating new instance: " + season + " " + year);
        AcademicCalendarActivity fragment = new AcademicCalendarActivity();

        Bundle args = new Bundle();
        args.putString("season", season);
        args.putString("year", year);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
    }
}
