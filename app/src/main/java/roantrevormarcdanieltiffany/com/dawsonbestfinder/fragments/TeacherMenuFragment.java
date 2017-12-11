package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.MenuActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.Teacher;

/**
 * Fragment which displays a list of teachers to choose from
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class TeacherMenuFragment extends Fragment {
    private final String TAG = TeacherMenuFragment.class.getSimpleName();

    ArrayAdapter<String> itemsAdapter;
    private OnItemSelectedListener listener;
    private ArrayList<Integer> teacherIndexes = new ArrayList<>();
    public static final String TEACHER_INDEXES = "teacherIndexes";
    private ArrayList<String> searchedTeachersNames = new ArrayList<>();
    private ArrayList<Integer> searchedTeachersIndexes = new ArrayList<>();

    /**
     * Override onCreate. Sets the teachers list
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Log.d(TAG, "savedInstanceState: " + savedInstanceState);
        Bundle bundle = getArguments();

        Log.d(TAG, "Bundle args: "  + bundle);

        if(bundle != null){
            teacherIndexes = getArguments().getIntegerArrayList(TEACHER_INDEXES);
            Log.d(TAG, "teacherIndexes: " + teacherIndexes);
        }

        for(int position:teacherIndexes) {
            Teacher teacherHolder = MenuActivity.teachers.get(position);
            searchedTeachersNames.add(teacherHolder.getFull_name());
            searchedTeachersIndexes.add(position);
        }

        itemsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchedTeachersNames);
    }

    /**
     * Before the fragment gets created or any views, called to set the context
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            this.listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement TeacherMenuFragment.OnItemSelectedListener");
        }
    }


    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onTeacherClick(int position);

    }

    /**
     * Inflates the {@code teachers_menu_fragment} layout
     *
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.teachers_menu_fragment, parent, false);
    }

    /**
     * Sets the onclick listeners for teacher once the view is created
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        ListView lvItems = view.findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onTeacherClick(searchedTeachersIndexes.get(position));
            }
        });
    }

}
