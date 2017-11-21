package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.TeacherContactActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;

/**
 * Created by sirMerr on 2017-11-18.
 */

public class TeacherContactFragment extends Fragment {
    private static final String TAG = TeacherContactFragment.class.getSimpleName();

    private Bundle bundle;
    private TeacherContactActivity teacherContactActivity;
    private Teacher teacher;

    /**
     * Inflates layout with layout of TeacherContactActivity
     *
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Called onCreateView");
        return inflater.inflate(R.layout.activity_contact_teacher, container, false);
    }

    /**
     * Get and display teacher information
     *
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Called onActivityCreated");
        teacherContactActivity =(TeacherContactActivity) getActivity();

        bundle = teacherContactActivity.getIntent().getExtras();

        String teacherId =  bundle.getString("teacherId");

        getTeacher(teacherId);

    }

    /**
     * Make teacher object from teacher id
     * @param teacherId
     */
    private void getTeacher(String teacherId) {
        Log.d(TAG, "Called getTeacher");
        Log.d(TAG, "TeacherId: " + teacherId);

        // @todo Connect to Firebase to get teacher
    }

    /**
     * Show teacher in layout
     */
    private void showTeacher() {
        EditText etFullName = teacherContactActivity.findViewById(R.id.etFullName);
        EditText etEmail = teacherContactActivity.findViewById(R.id.etEmail);
        EditText etLocal = teacherContactActivity.findViewById(R.id.etLocal);
        EditText etDepartment = teacherContactActivity.findViewById(R.id.etDepartments);
        EditText etOffice = teacherContactActivity.findViewById(R.id.etOffice);
        EditText etPositions = teacherContactActivity.findViewById(R.id.etPositions);
        EditText etSectors = teacherContactActivity.findViewById(R.id.etSectors);

        etFullName.setText(teacher.getFullName());
        etEmail.setText(teacher.getEmail());
        etLocal.setText(teacher.getLocal());
        etDepartment.setText(teacher.getDepartments().toString());
        etOffice.setText(teacher.getOffice());
        etPositions.setText(teacher.getPositions().toString());
        etSectors.setText(teacher.getSectors().toString());
    }
}
