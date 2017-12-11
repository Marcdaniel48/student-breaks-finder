package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherDetailFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 * Activity which allows the user to search for a teacher with a first name,
 * last name and either an exact or "like" search. It looks through the data
 * in our Firebase database and launches either ChooseTeacherActivity if there
 * are more than 1 teachers found from the search, or TeacherContactActivity
 * if there is only 1 or 0.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class FindTeacherActivity extends MenuActivity {
    private static final String TAG = FindTeacherActivity.class.getSimpleName();
    private Context context;
    private EditText etFirstName, etLastName;
    private RadioButton rbExactSearch;
    private Button bSearch;

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
        setContentView(R.layout.activity_find_teacher);
        context = this.getApplicationContext();

        etFirstName =  findViewById(R.id.etFirstName);
        etLastName =  findViewById(R.id.etLastName);
        rbExactSearch = findViewById(R.id.rbExactSearch);
        bSearch = findViewById(R.id.btnSearch);
    }

    /**
     * Search and open either ChooseTeacherActivity
     * or TeacherContactActivity
     *
     * Exact search must have whatever was entered be the same,
     * like will implement a search such as SQL's LIKE
     * @param view
     */
    public void searchClick(View view) {
        Log.d(TAG, "Called searchClick()");
        // Make sure search isn't called before teachers is filled
        if (teachers.isEmpty()) {
            Log.d(TAG, "The teachers array is empty. Please wait a few seconds.");
            return;
        } else if (!checkFields()) {
            return;
        }

        // If they've reached this point, one or both are filled
        Log.d(TAG, "First name and/or last name field(s) are filled.");
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        boolean exactSearch = rbExactSearch.isChecked();

        // Find teachers with a search
        ArrayList<Integer> teacherIndexes = search(exactSearch, firstName, lastName);

        // Start appropriate activity
        startCorrectActivity(teacherIndexes);
    }

    /**
     * Checks that at least one of the fields is not empty
     * and handles it accordingly.
     *
     * @return true if one is filled, false if both are empty
     */
    public boolean checkFields() {
        Log.d(TAG, "Called checkFields()");
        if (etFirstName.getText().length() == 0 && etLastName.getText().length() == 0) {
            Log.d(TAG, "Both first and last name fields are empty.");
            // Changes underline to red and does nothing
            // if both first and last name are empty
            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_empty}
            };

            int[] colors = new int[] {
                    Color.RED,
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);

            etFirstName.setBackgroundTintList(colorStateList);
            etLastName.setBackgroundTintList(colorStateList);
            return false;
        }
        return true;
    }

    /**
     * Sets the intent and starts the activity based on
     * if no teacher was found, one teacher was found or more was found.
     *
     * 0 or 1 sends it to TeacherContactActivity
     * 2+ sends it to ChooseTeacherActivity
     *
     * @param teacherIndexes array of indexes of found teachers
     */
    public void startCorrectActivity(ArrayList<Integer> teacherIndexes) {
        Log.d(TAG, "Called startCorrectActivity(ArrayList<Integer> teacherIndexes)");
        Log.d(TAG, "Teacher Indexes size: " + teacherIndexes.size());

        if (teacherIndexes.isEmpty()) {
            Intent i = new Intent(context, TeachersActivity.class);
            i.putIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES, teacherIndexes);
            startActivity(i);
        } else if (teacherIndexes.size() == 1) {
            Intent i = new Intent(context, TeachersActivity.class);
            i.putIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES, teacherIndexes);

            startActivity(i);
        } else {
            // At this point, there are more than 1 teacher found
            Intent i = new Intent(context, TeachersActivity.class);
            i.putIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES, teacherIndexes);
            startActivity(i);
        }
    }

    /**
     * Search for teachers with corresponding fields
     *
     * @param exactSearch
     * @param firstName
     * @param lastName
     * @return Array of integers or empty array
     */
      protected ArrayList<Integer> search(boolean exactSearch, String firstName, String lastName) {
        Log.d(TAG, "Called search(boolean exactSearch, String firstName, String lastName)");
        ArrayList<Integer> teacherIndexes = new ArrayList<>();

        if (exactSearch) {
            Log.d(TAG, "In exact search.");
            // Whole name must be correct
            for (int i = 0; i < teachers.size(); i++) {
                String holderFirstName = teachers.get(i).getFirst_name();
                String holderLastName = teachers.get(i).getLast_name();

                // Add teacher index to array if the full name is the same
                // or the first or last name are exact if only one of them is specified
                if (teachers.get(i).getFull_name().matches(firstName + " " + lastName)
                        || (firstName.length() > 0 && holderFirstName.matches(firstName) && lastName.length() <= 0)
                        || (lastName.length() > 0 && holderLastName.matches(lastName) && firstName.length() <= 0)) {
                    teacherIndexes.add(i);
                }
            }
        } else {
            Log.d(TAG, "In like search");
            // First/Last name must contain the fields provided
            for (int i = 0; i < teachers.size(); i++) {
                if ((firstName.length() > 0 && teachers.get(i).getFirst_name().matches(".*"+ firstName + ".*"))
                        || (lastName.length() > 0 && teachers.get(i).getLast_name().matches(".*"+ lastName + ".*"))) {
                    teacherIndexes.add(i);
                }
            }
        }

        //if teachers has not been loaded yet, load them.
        if (teachers.isEmpty()) {
            authFirebase();
            getDB();
        }

        return teacherIndexes;
    }

    /**
     * Inserts the given fragment into the content view using
     * the fragment manager.
     */
    public void createFragments(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.teachersContent, fragment);
        transaction.commit();
    }

    /**
     * Displays info about the teacher
     *
     * @param teacher
     * @param teacherIndex
     */
    public void showTeacherContactActivity(Teacher teacher, int teacherIndex) {
        Intent i = new Intent(context,TeachersActivity.class);
        i.putExtra(TeacherContactActivity.TEACHER_ID, teacherIndex);
        i.putExtra(TeacherContactActivity.FULL_NAME, teacher.getFull_name());
        i.putExtra(TeacherContactActivity.BIO, teacher.getBio());
        i.putExtra(TeacherContactActivity.LOCAL, teacher.getLocal());
        i.putExtra(TeacherContactActivity.WEBSITE, teacher.getWebsite());
        i.putExtra(TeacherContactActivity.OFFICE, teacher.getOffice());
        i.putExtra(TeacherContactActivity.EMAIL, teacher.getEmail());
        i.putStringArrayListExtra(TeacherContactActivity.DEPARTMENTS, (ArrayList<String>) teacher.getDepartments());
        i.putStringArrayListExtra(TeacherContactActivity.SECTORS, (ArrayList<String>) teacher.getSectors());
        i.putStringArrayListExtra(TeacherContactActivity.POSITIONS, (ArrayList<String>) teacher.getPositions());

        startActivity(i);
    }
}
