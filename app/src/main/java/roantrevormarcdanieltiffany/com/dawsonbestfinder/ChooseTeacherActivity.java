package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;

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
public class ChooseTeacherActivity extends FindTeacherActivity{
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    protected static final String TEACHER_INDEXES = "teacherIndexes";
    private ArrayAdapter<String> adapterString;
    private ListView lv;
    private Context context;
    private ArrayList<Integer> teacherIndexes = new ArrayList<>();

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
        Log.d(TAG, "called onCreate()");
        setContentView(R.layout.activity_choose_teacher);
        context = this.getApplicationContext();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        teacherIndexes = extras.getIntegerArrayList(TEACHER_INDEXES);

        Log.d(TAG, "Teacher Indexes Array: " + teacherIndexes.toString());
        lv = findViewById(R.id.lvTeachers);

        setListView();
    }

    /**
     * Set the ListView with the found teachers
     * (first and last name). on teacher click, go to TeacherContactActivity
     */
    private void setListView() {
        if (teachers.isEmpty()) {
            return;
        }
        Log.d(TAG, "Called setListView()");

        // This is accessed within inner class and needs to be final
        final List<Teacher> foundTeachers = new ArrayList<>();
        List<String> foundTeachersNames = new ArrayList<>();

        // Find teachers from {@code teachers} array that we want
        // and their full names
        for (int index:teacherIndexes) {
            foundTeachers.add(teachers.get(index));
            foundTeachersNames.add(teachers.get(index).getFull_name());
        }

        // Set adapter
        adapterString = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foundTeachersNames);
        lv.setAdapter(adapterString);

        // Click listeners on items that pass the category and quote index
        // to QuoteActivity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Teacher clickedTeacher = foundTeachers.get(position);
                showTeacherContactActivity(clickedTeacher, teachers.indexOf(clickedTeacher));
            }
        });

    }
}
