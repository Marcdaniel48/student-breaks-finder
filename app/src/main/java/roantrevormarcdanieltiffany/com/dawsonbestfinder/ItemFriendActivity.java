package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemFriendActivity extends Activity
{
    TextView tvFriend;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemfriends);

        tvFriend = findViewById(R.id.tvItemFriend);
    }

}
