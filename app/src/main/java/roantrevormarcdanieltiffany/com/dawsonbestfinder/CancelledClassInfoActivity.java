package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CancelledClassInfoActivity extends MenuActivity {

    private final String TAG = "CC Info Activity";

    TextView courseTitleTV, courseNumberTV, courseTeacherTV, dateCancelledTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class_info);
        Log.d(TAG, "onCreate: was called");
        courseNumberTV = (TextView) findViewById(R.id.course_number);
        courseTeacherTV = (TextView) findViewById(R.id.course_teacher);
        courseTitleTV = (TextView) findViewById(R.id.course_title);
        dateCancelledTV = (TextView) findViewById(R.id.course_date_cancelled);

        courseTeacherTV.setText(getIntent().getExtras().getString("teacher"));
        courseTitleTV.setText(getIntent().getExtras().getString("title"));
        dateCancelledTV.setText(getIntent().getExtras().getString("date"));
        courseNumberTV.setText(getIntent().getExtras().getString("course"));

        //set a click event for the teacher to launch a teacher info activity

    }
}
