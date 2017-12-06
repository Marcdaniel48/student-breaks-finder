package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ItemFriendActivity extends Activity
{
    private static final String TAG = ItemFriendActivity.class.getSimpleName();

    private static final String EMAIL_KEY = "email";

    TextView tvFriendCourse, tvFriendSection;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemfriends);

        String friendEmail = getIntent().getExtras().getString(EMAIL_KEY);

        tvFriendCourse = findViewById(R.id.tvFriendCourse);
        tvFriendSection = findViewById(R.id.tvFriendSection);
    }

}
