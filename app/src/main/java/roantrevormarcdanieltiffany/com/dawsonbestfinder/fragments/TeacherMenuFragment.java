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
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;

/**
 * Created by sirMerr on 2017-12-10.
 */

public class TeacherMenuFragment extends Fragment {
    private final String TAG = TeacherMenuFragment.class.getSimpleName();

    ArrayAdapter<String> itemsAdapter;
    private OnItemSelectedListener listener;
    private ArrayList<Integer> teacherIndexes = new ArrayList<>();
    public static final String TEACHER_INDEXES = "teacherIndexes";
    private ArrayList<String> searchedTeachersNames = new ArrayList<>();
    private ArrayList<Integer> searchedTeachersIndexes = new ArrayList<>();

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

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){      // context instanceof YourActivity
            this.listener = (OnItemSelectedListener) context; // = (YourActivity) context
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.teachers_menu_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
