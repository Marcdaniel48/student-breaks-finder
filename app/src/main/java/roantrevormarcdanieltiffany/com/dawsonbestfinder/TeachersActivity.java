package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherDetailFragment;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments.TeacherMenuFragment;

/**
 * Created by sirMerr on 2017-12-10.
 */

public class TeachersActivity extends MenuActivity implements TeacherMenuFragment.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TeacherMenuFragment firstFragment;
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        firstFragment = new TeacherMenuFragment();

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            // Instance of first fragment
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        } else {
            // if this is not done, the menu fragment ended up duplicate
            //  on rotate back to landscape, only when the menu activity was
            //  last active
            ft.replace(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            TeacherDetailFragment secondFragment = new TeacherDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
        }
    }

    @Override
    public void onPizzaItemSelected(int position) {
        Toast.makeText(this, "Called By Fragment A: position - "+ position, Toast.LENGTH_SHORT).show();

        // Load Pizza Detail Fragment
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
