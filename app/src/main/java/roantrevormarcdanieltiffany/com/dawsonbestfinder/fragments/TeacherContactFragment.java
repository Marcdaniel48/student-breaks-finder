package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.MenuActivity;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;
import roantrevormarcdanieltiffany.com.dawsonbestfinder.entities.Teacher;

/**
 * Fragment which displays all information about a teacher
 * and allows the user to launch an intent to call them (ACTION_DIAL)
 * or email them if they have the proper information
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class TeacherContactFragment extends Fragment{
    private final String TAG = TeacherContactFragment.class.getSimpleName();
    public final static String POSITION_PARAM = "position";
    int position = 0;

    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvLocal;
    private TextView tvDepartment;
    private TextView tvOffice;
    private TextView tvPositions;
    private TextView tvSectors;
    private TextView tvWebsite;
    private TextView tvImage;
    private TextView tvBio;
    private TableLayout tl;

    /**
     * Get the teacher clicked on or searched
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt(POSITION_PARAM, 0);
            }
        }
    }

    /**
     * Inflates the {@code teacher_detail_fragment} layout
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.teacher_detail_fragment, parent, false);
    }

    /**
     * Once the view is created, find views by id and update the view
     * with the teacher's information
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        tvFullName = view.findViewById(R.id.etFullName);
        tvEmail = view.findViewById(R.id.etEmail);
        tvLocal = view.findViewById(R.id.etLocal);
        tvDepartment = view.findViewById(R.id.etDepartments);
        tvOffice = view.findViewById(R.id.etOffice);
        tvPositions = view.findViewById(R.id.etPositions);
        tvSectors = view.findViewById(R.id.etSectors);
        tvWebsite = view.findViewById(R.id.etWebsite);
        tvImage = view.findViewById(R.id.etImage);
        tvBio = view.findViewById(R.id.etBio);
        tl = view.findViewById(R.id.tl);

        updateView(position);
    }

    /**
     * Updates the teacher's information on the view
     *
     * @param position
     */
    public void updateView(int position){
        final Teacher teacher = MenuActivity.teachers.get(position);
        // Set text views to the teacher's info
        tvFullName.setText(teacher.getFull_name());
        tvEmail.setText(teacher.getEmail());
        tvLocal.setText(teacher.getLocal());
        tvDepartment.setText(teacher.getDepartments().toString());
        tvOffice.setText(teacher.getOffice());
        tvPositions.setText(teacher.getPositions().toString());
        tvSectors.setText(teacher.getSectors().toString());
        tvWebsite.setText(teacher.getWebsite());
        tvImage.setText(teacher.getImage());
        tvBio.setText(teacher.getBio());

        // Set onclick on local to start call intent
        tvLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Called tvLocal->onClick()");
                if (teacher.getLocal().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:15149348731," + teacher.getLocal()));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        // Add onclick to email to open email app
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Called tvEmail->onClick()");
                if (teacher.getEmail().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {teacher.getEmail()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "from: dabestfinder");
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        // Underline to make it more visible that this is clickable
        tvLocal.setPaintFlags(tvLocal.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvEmail.setPaintFlags(tvEmail.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }
}
