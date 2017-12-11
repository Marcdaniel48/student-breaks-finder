package roantrevormarcdanieltiffany.com.dawsonbestfinder;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

/**
 * Java class for the About activity.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class AboutActivity extends Activity
{
    TextView marcdanielTextView, tiffanyTextView, trevorTextView, roanTextView, courseidTextView;

    /**
     * onCreate. Sets up the About activity's text views and clickable elements.
     * @param savedInstanceBundle
     */
    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_about);

        marcdanielTextView = findViewById(R.id.marcdanielTextView);
        tiffanyTextView = findViewById(R.id.tiffanyTextView);
        trevorTextView = findViewById(R.id.trevorTextView);
        roanTextView = findViewById(R.id.roanTextView);

        courseidTextView = findViewById(R.id.courseidTextView);

        // Clicking on the Dawson College Course ID will launch the Dawson Computer Science page
        courseidTextView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String url = "https://www.dawsoncollege.qc.ca/computer-science-technology/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                if (i.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(i);
                }
            }
        });

        // When the user clicks on any of the authors' names, their individual blurbs appear.
        setOnClickBlurb(marcdanielTextView, R.string.about_blurb_marcdaniel);
        setOnClickBlurb(tiffanyTextView, R.string.about_blurb_tiffany);
        setOnClickBlurb(trevorTextView, R.string.about_blurb_trevor);
        setOnClickBlurb(roanTextView, R.string.about_blurb_roan);
    }

    /**
     * Used for setting an OnClickListener to each of the team members' Text Views. When the name of one of the authors gets clicked, a blurb about them will appear
     * in an AlertDialog.
     *
     * @param tv
     * @param blurbID
     */
    private void setOnClickBlurb(TextView tv, final int blurbID)
    {
        tv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);

                builder.setMessage(getResources().getString(blurbID)).setTitle(R.string.about_blurb);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}
