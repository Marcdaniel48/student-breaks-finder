package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.os.Bundle;

/**
 * Activity which displays a ListView of teachers
 * matching the search done by the user in FindTeacherActivity.
 * The user can choose which teacher they want to see more information about,
 * which will open TeacherContactActivity
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class ChooseTeacherActivity extends MenuActivity{
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    protected static final String TEACHER_INDEXES = "teacherIndexes";

    /**
     * When invoked, will set up the activity.
     * Assigns the list view, get the data from firebase
     * and set the list of category titles
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher);

    }


}
