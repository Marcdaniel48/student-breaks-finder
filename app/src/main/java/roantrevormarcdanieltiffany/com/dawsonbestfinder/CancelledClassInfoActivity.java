package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * CancelledClassInfo activity to display information about specific cancelled classes
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 *
 */
public class CancelledClassInfoActivity extends MenuActivity {

    private final String TAG = CancelledClassInfoActivity.class.getSimpleName();

    private final String TEACHER_EXTRA_KEY = "teacher";
    private final String TITLE_EXTRA_KEY = "title";
    private final String DATE_EXTRA_KEY = "date";
    private final String CODE_EXTRA_KEY = "code";

    TextView courseTitleTV, courseNumberTV, courseTeacherTV, dateCancelledTV;


    /**
     * Create the activity and fill the info
     * @author Tiffany Le-Nguyen
     * @author Roan Chamberlain
     * @author Marc-Daniel Dialogo
     * @author Trevor Eames
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class_info);
        Log.d(TAG, "onCreate: was called");
        courseNumberTV = (TextView) findViewById(R.id.course_number);
        courseTeacherTV = (TextView) findViewById(R.id.course_teacher);
        courseTitleTV = (TextView) findViewById(R.id.course_title);
        dateCancelledTV = (TextView) findViewById(R.id.course_date_cancelled);

        courseTeacherTV.setText(getIntent().getExtras().getString(TEACHER_EXTRA_KEY));
        courseTitleTV.setText(getIntent().getExtras().getString(TITLE_EXTRA_KEY));
        dateCancelledTV.setText(getIntent().getExtras().getString(DATE_EXTRA_KEY));
        courseNumberTV.setText(getIntent().getExtras().getString(CODE_EXTRA_KEY));

        //set a click event for the teacher to launch a teacher info activity

    }
}
