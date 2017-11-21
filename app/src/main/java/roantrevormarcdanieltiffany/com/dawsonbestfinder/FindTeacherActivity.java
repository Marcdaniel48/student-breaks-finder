package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sirMerr on 2017-11-18.
 */

public class FindTeacherActivity extends MenuActivity {
    private static final String TAG = ChooseTeacherActivity.class.getSimpleName();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
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
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        etFirstName =  findViewById(R.id.etFirstName);
        etLastName =  findViewById(R.id.etLastName);
        rbExactSearch = findViewById(R.id.rbExactSearch);
    }

    private void getDB() {
        Log.d(TAG, "Called getDB()");

        DatabaseReference myRef = database.getReference("dawsonbestfinder");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Snapshot: " + dataSnapshot);

                for (DataSnapshot categorySnap: dataSnapshot.getChildren()) {

                }
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
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
     * @param view
     */
    public void searchClick(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        boolean exactSearch = rbExactSearch.isChecked();

        Intent i = new Intent(context, TeacherContactActivity.class)
        i.putExtra("firstName", firstName);
        i.putExtra("lastName", lastName);
        i.putExtra("exactSearch", exactSearch);
        startActivity(i);
    }
}
