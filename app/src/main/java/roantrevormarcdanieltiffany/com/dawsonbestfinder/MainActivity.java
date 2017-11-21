package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDawsonButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onClassCancellations() {
        //Intent i = new Intent();
    }

    public void onFindTeacher() {
        //Intent i = new Intent();
    }

    public void onAddToCalendar() {
        //Intent i = new Intent();
    }

    public void onNotes() {
        //Intent i = new Intent();
    }

    public void onWeather() {
        //Intent i = new Intent();
    }

    public void onAcademicCalendar() {
        //Intent i = new Intent();
    }
}


