package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherDetailFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 * Created by sirMerr on 2017-12-10.
 */

public class TeachersActivity extends MenuActivity implements TeacherMenuFragment.OnItemSelectedListener {
    private final String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_main);
        TeacherMenuFragment firstFragment;
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        firstFragment = new TeacherMenuFragment();

        Log.d(TAG, getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            ArrayList<Integer> indexes = getIntent().getIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES);
            Log.d(TAG, "Indexes: " + indexes);
            Bundle args = new Bundle();
            args.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES, indexes);
            // Instance of first fragment
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            firstFragment.setArguments(args);
            ft.add(R.id.flContainer, firstFragment);
            ft.commit();
        } else {
            ArrayList<Integer> indexes = getIntent().getIntegerArrayListExtra(TeacherMenuFragment.TEACHER_INDEXES);
            Log.d(TAG, "Indexes: " + indexes);
            Bundle args = new Bundle();
            args.putIntegerArrayList(TeacherMenuFragment.TEACHER_INDEXES, indexes);
            // If this is not done, the menu fragment ended up duplicate
            // on rotate back to landscape, only when the menu activity was
            // last active
            firstFragment.setArguments(args);
            ft.replace(R.id.flContainer, firstFragment);
            ft.commit();
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            TeacherDetailFragment secondFragment = new TeacherDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            // Communicate with Fragment using Bundle
            secondFragment.setArguments(args);
            // Begin  FragmentTransaction
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.add(R.id.flContainer2, secondFragment);
            ft2.commit();
        }
    }

    @Override
    public void onTeacherClick(int position) {
        Toast.makeText(this, "Called By Fragment A: position - "+ position, Toast.LENGTH_SHORT).show();

        // Load Detail Fragment
        TeacherDetailFragment secondFragment = new TeacherDetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
                    //.addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }
    }
}
