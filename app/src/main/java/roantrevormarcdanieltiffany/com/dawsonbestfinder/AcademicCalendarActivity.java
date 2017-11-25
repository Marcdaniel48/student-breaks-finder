package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

/**
 * Created by mrtvor on 2017-11-24.
 */

public class AcademicCalendarActivity extends WebViewFragment {

    private final static String TAG ="FRAG-WEBVIEIWFRAGMENT";
    String url = "https://www.dawsoncollege.qc.ca/registrar/";
    String fullUrl = "https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/";

    public static AcademicCalendarActivity newInstance(String season, String year) {
        Log.v(TAG, "Creating new instance: " + season + " " + year);
        AcademicCalendarActivity fragment = new AcademicCalendarActivity();

        Bundle args = new Bundle();
        args.putString("season", season);
        args.putString("year", year);
        fragment.setArguments(args);
        return fragment;
    }

    public String getShownSeason() {
        String season = "";
        Bundle args = getArguments();
        if(args.getString("season") != null) {
            season = args.getString("season","fall");
        }

        return season;
    }

    public String getShownYear() {
        String year = "";
        Bundle args = getArguments();
        if(args.getString("year") != null) {
            year = args.getString("year", "2017");
        }

        return year;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "FRAG onResume(): " + getShownSeason() + getShownYear());
    }

    @Override
    public void onStart() {
        Log.d(TAG, "FRAG onStart: " + getShownSeason() + getShownYear());
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "FRAG onActivityCreated(): " + getShownSeason() + getShownYear());
        super.onActivityCreated(savedInstanceState);

        String season = getShownSeason();
        String year = getShownYear();

        WebView webview = getWebView();
        webview.setPadding(2,2,2,2);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebViewClient(new WebViewClient());

        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        if(season != "" && year != "") {
            if(season == "summer") {
                webview.loadUrl(url + season + "-" + year + "/");
            } else {
                webview.loadUrl(url + season + "-" + year + "-day-division/");
            }
        } else {
            webview.loadUrl(fullUrl);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "FRAG onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(): " + getShownSeason() + getShownYear());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "FRAG onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "FRAH onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "FRAG onPause(): " + getShownSeason() + getShownYear());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "FRAG onStop(): " + getShownSeason() + getShownYear());
        super.onStop();
    }
}
