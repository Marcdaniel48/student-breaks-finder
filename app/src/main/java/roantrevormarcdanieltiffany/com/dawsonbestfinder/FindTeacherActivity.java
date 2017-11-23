package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
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

import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;

/**
 * Created by sirMerr on 2017-11-18.
 */

public class FindTeacherActivity extends MenuActivity {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email = "letiffany.nguyen@gmail.com";
    private String password = "admindsa-dabesteam";
    private Context context;
    private EditText etFirstName, etLastName;
    private RadioButton rbExactSearch;

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

        authFirebase();
        getDB();
    }

    /**
     * Initiate FirebaseAuth and AUthStateListener to track
     * whenever a user signs in or out
     */
    private void authFirebase() {
        mAuth = FirebaseAuth.getInstance();
        signIn(email, password);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    /**
     * Signs into firebase with email and password
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(FindTeacherActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getDB() {
        Log.d(TAG, "Called getDB()");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("teachers");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Snapshot: " + dataSnapshot);

                for (DataSnapshot teacherSnap: dataSnapshot.getChildren()) {
                    teachers.add(teacherSnap.getValue(Teacher.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
            Log.d(TAG, "The teachers array is empty. Getting out.");
            return;
        } else if (checkFields()) {
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
    private boolean checkFields() {
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
    private void startCorrectActivity(ArrayList<Integer> teacherIndexes) {
        if (teacherIndexes.isEmpty()) {
            Intent i = new Intent(context, TeacherContactActivity.class);
            i.putExtra(TeacherContactActivity.TEACHER_ID, -1);
            startActivity(i);
        } else if (teacherIndexes.size() == 1) {
            Intent i = new Intent(context, TeacherContactActivity.class);
            i.putExtra(TeacherContactActivity.TEACHER_ID, teacherIndexes.get(0));
            startActivity(i);
        } else {
            // At this point, there are more than 1 teacher found
            Intent i = new Intent(context, ChooseTeacherActivity.class);
            i.putExtra(ChooseTeacherActivity.TEACHER_INDEXES, teacherIndexes);
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
    private ArrayList<Integer> search(boolean exactSearch, String firstName, String lastName) {
        ArrayList<Integer> teacherIndexes = new ArrayList<>();

        if (exactSearch) {
            Log.d(TAG, "In exact search.");
            // Whole name must be correct
            for (int i = 0; i < teachers.size(); i++) {
                // @todo The exact search to be defined better
                if (teachers.get(i).getFull_name().equals(firstName + " " + lastName)) {
                    teacherIndexes.add(i);
                }
            }
        } else {
            Log.d(TAG, "In like search");
            // First/Last name must contain the fields provided
            for (int i = 0; i < teachers.size(); i++) {
                if ((firstName.length() > 0 && teachers.get(i).getFirst_name().matches(firstName))
                        || (lastName.length() > 0 && teachers.get(i).getLast_name().matches(lastName))) {
                    teacherIndexes.add(i);
                }
            }
        }

        return teacherIndexes;
    }
}
