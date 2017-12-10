package roantrevormarcdanieltiffany.com.dawsonbestfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.R;

/**
 * Created by sirMerr on 2017-12-10.
 */

public class TeacherDetailFragment extends Fragment{
    int position = 0;
    TextView tvTitle;
    TextView tvDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.teacher_detail_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvDetails = (TextView) view.findViewById(R.id.tvDetails);

        String[] example = {"Tiff", "Me!"};
        String[] exampleDetails = {"Girl", "lol!"};

        // update view
        tvTitle.setText(example[position]);
        tvDetails.setText(exampleDetails[position]);
    }

    // Activity is calling this to update view on Fragment
    public void updateView(int position){
        String[] example = {"Tiff", "Me!"};
        String[] exampleDetails = {"Girl", "lol!"};
        tvTitle.setText(example[position]);
        tvDetails.setText(exampleDetails[position]);
    }
}
