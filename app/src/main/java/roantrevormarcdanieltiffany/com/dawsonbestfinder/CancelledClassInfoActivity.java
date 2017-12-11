package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 * CancelledClassInfo activity to display information about specific cancelled classes
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 *
 */
public class CancelledClassInfoActivity extends FindTeacherActivity {

    private final String TAG = CancelledClassInfoActivity.class.getSimpleName();

    private final String TEACHER_EXTRA_KEY = "teacher";
    private final String TITLE_EXTRA_KEY = "title";
    private final String DATE_EXTRA_KEY = "date";
    private final String CODE_EXTRA_KEY = "code";
    private Context context;

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
        context = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class_info);
        Log.d(TAG, "onCreate: was called");
        courseNumberTV = findViewById(R.id.course_number);
        courseTeacherTV = findViewById(R.id.course_teacher);
        courseTitleTV = findViewById(R.id.course_title);
        dateCancelledTV = findViewById(R.id.course_date_cancelled);

        courseTeacherTV.setText(getIntent().getExtras().getString(TEACHER_EXTRA_KEY));
        courseTitleTV.setText(getIntent().getExtras().getString(TITLE_EXTRA_KEY));
        dateCancelledTV.setText(getIntent().getExtras().getString(DATE_EXTRA_KEY));
        courseNumberTV.setText(getIntent().getExtras().getString(CODE_EXTRA_KEY));

        courseTeacherTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String teachername = getIntent().getExtras().getString(TEACHER_EXTRA_KEY);
                String teacherfname = teachername.split(" ")[0];
                String teacherlname = teachername.split(" ")[1];


                Log.i(TAG, "onClick: " + teachername + " was tapped");

                ArrayList<Integer> indexes = search(true, teacherfname, teacherlname);

                Intent i = new Intent(context, TeachersActivity.class);
                i.putIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES, indexes);
                startActivity(i);

            }
        });

        //set a click event for the teacher to launch a teacher info activity

    }
}
