package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherContactFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 *
 * Activity which displays a ListView of teachers
 * matching the search done by the user in FindTeacherActivity.
 * The user can choose which teacher they want to see more information about,
 * which will open TeacherContactActivity
 *
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames

 */
public class ChooseTeacherActivity extends MenuActivity implements TeacherMenuFragment.OnItemSelectedListener {
    private final String TAG = ChooseTeacherActivity.class.getSimpleName();
    ArrayList<Integer> indexes;

    /**
     * Overrides onCreate to set up the list of teachers to choose from
     * and handle the fragment building
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_main);
        TeacherMenuFragment firstFragment;

        Log.d(TAG, getResources().getConfiguration().orientation + "");

        Bundle args = new Bundle();

        if (savedInstanceState == null) {
            indexes = getIntent().getIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES);
        } else {
            indexes = savedInstanceState.getIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES);
        }

        Log.d(TAG, "Indexes: " + indexes);
        args.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES, indexes);

            FragmentTransaction ft =
                    getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            firstFragment = new TeacherMenuFragment();

            if (indexes.size() == 1) {
                Log.d(TAG, "Index 1");
                onTeacherClick(indexes.get(0));
            } else {
                Log.d(TAG, "Index not 1");
                // Instance of first fragment
                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                firstFragment.setArguments(args);
                ft.add(R.id.flContainer, firstFragment);
                ft.commit();

            }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(TAG, "Orientation landscape ");
                TeacherContactFragment secondFragment = new TeacherContactFragment();
                Bundle args2 = new Bundle();
                args2.putInt(TeacherContactFragment.POSITION_PARAM, indexes.get(0));
                // Communicate with Fragment using Bundle
                secondFragment.setArguments(args2);
                // Begin  FragmentTransaction
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.add(R.id.flContainer2, secondFragment);
                ft2.commit();
            }
    }

    /**
     * Starts the TeacherContactFragment with the teacher the user clicked on
     *
     * @param position
     */
    @Override
    public void onTeacherClick(int position) {
        Log.d(TAG, "called onTeacherClick");
        // Load Detail Fragment
        TeacherContactFragment secondFragment = new TeacherContactFragment();
        TeacherMenuFragment firstFragment = new TeacherMenuFragment();

        Bundle args = new Bundle();
        args.putInt(TeacherContactFragment.POSITION_PARAM, position);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            if (indexes.size() == 1) {
                Bundle args2 = new Bundle();
                ArrayList<Integer> indexes = new ArrayList<>();
                indexes.add(position);
                args2.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES,indexes);
                firstFragment.setArguments(args2);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, firstFragment)
                        .commit();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
                    //.addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .commit();
        }
    }

    /**
     * Save the teacher indexes, overrides onSaveInstanceState
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES, indexes);
    }
}
