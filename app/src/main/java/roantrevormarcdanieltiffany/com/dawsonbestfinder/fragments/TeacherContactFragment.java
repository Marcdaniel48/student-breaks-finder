package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.TeacherContactActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.beans.Teacher;

/**
 * Fragment which displays all information about a teacher
 * and allows the user to launch an intent to call them (ACTION_DIAL)
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
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

        teacher = new Teacher();

        int teacherId =  bundle.getInt(TeacherContactActivity.TEACHER_ID);
        Log.d(TAG, "Teacher ID: " + teacherId);

        if (teacherId == -1) {
            showNone();
        } else {
            teacher.setBio(bundle.getString(TeacherContactActivity.BIO));
            teacher.setFull_name(bundle.getString(TeacherContactActivity.FULL_NAME));
            teacher.setWebsite(bundle.getString(TeacherContactActivity.WEBSITE));
            teacher.setEmail(bundle.getString(TeacherContactActivity.EMAIL));
            teacher.setLocal(bundle.getString(TeacherContactActivity.LOCAL));
            teacher.setDepartments(bundle.getStringArrayList(TeacherContactActivity.DEPARTMENTS));
            teacher.setPositions(bundle.getStringArrayList(TeacherContactActivity.POSITIONS));
            teacher.setSectors(bundle.getStringArrayList(TeacherContactActivity.SECTORS));
            teacher.setOffice(bundle.getString(TeacherContactActivity.OFFICE));
            showTeacher();
        }
    }

    /**
     * Shows message that no teacher with these specs were found
     */
    private void showNone() {
        TextView tvNone = teacherContactActivity.findViewById(R.id.tvNone);

        tvNone.setText(R.string.no_teacher_found);
    }
    /**
     * Show teacher fragment
     *
     * @todo Find out and implement what to show for teacher's image
     * @body Ex: `"image": "faugustin.jpg"`, do we just show this string? There's no url.
     */
    private void showTeacher() {
        Log.d(TAG, "Called showTeacher()");
        EditText etFullName = teacherContactActivity.findViewById(R.id.etFullName);
        EditText etEmail = teacherContactActivity.findViewById(R.id.etEmail);
        EditText etLocal = teacherContactActivity.findViewById(R.id.etLocal);
        EditText etDepartment = teacherContactActivity.findViewById(R.id.etDepartments);
        EditText etOffice = teacherContactActivity.findViewById(R.id.etOffice);
        EditText etPositions = teacherContactActivity.findViewById(R.id.etPositions);
        EditText etSectors = teacherContactActivity.findViewById(R.id.etSectors);
        Button btnCall = teacherContactActivity.findViewById(R.id.btnCall);

        etFullName.setText(teacher.getFull_name());
        etEmail.setText(teacher.getEmail());
        etLocal.setText(teacher.getLocal());
        etDepartment.setText(teacher.getDepartments().toString());
        etOffice.setText(teacher.getOffice());
        etPositions.setText(teacher.getPositions().toString());
        etSectors.setText(teacher.getSectors().toString());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Called btnCall->onClick()");
                if (teacher.getLocal().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    /**
                     * @todo Check what is the phone number that we are supposed to be inputing for call intent.
                     * @body  As of right now, the intent opens the call screen with the teacher's local, check specs for more info.
                     */
                    intent.setData(Uri.parse("tel:" + teacher.getLocal()));
                    if (intent.resolveActivity(teacherContactActivity.getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
