package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherDetailFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 * Created by sirMerr on 2017-12-10.
 */

public class TeachersActivity extends MenuActivity implements TeacherMenuFragment.OnItemSelectedListener {
    private final String TAG = TeachersActivity.class.getSimpleName();
    ArrayList<Integer> indexes;

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
                TeacherDetailFragment secondFragment = new TeacherDetailFragment();
                Bundle args2 = new Bundle();
                args2.putInt(TeacherDetailFragment.POSITION_PARAM, indexes.get(0));
                // Communicate with Fragment using Bundle
                secondFragment.setArguments(args2);
                // Begin  FragmentTransaction
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.add(R.id.flContainer2, secondFragment);
                ft2.commit();
            }
    }

    @Override
    public void onTeacherClick(int position) {
        Log.d(TAG, "called onTeacherClick");
        // Load Detail Fragment
        TeacherDetailFragment secondFragment = new TeacherDetailFragment();
        TeacherMenuFragment firstFragment = new TeacherMenuFragment();

        Bundle args = new Bundle();
        args.putInt(TeacherDetailFragment.POSITION_PARAM, position);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES, indexes);
    }
}
