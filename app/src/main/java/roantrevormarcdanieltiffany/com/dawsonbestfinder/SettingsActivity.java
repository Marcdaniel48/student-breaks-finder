package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity
{
    EditText firstNameET, lastNameET, emailET, passwordET, datestampET;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        firstNameET = (EditText) findViewById(R.id.firstNameEditText);
        lastNameET = (EditText) findViewById(R.id.lastNameEditText);
        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        datestampET = (EditText) findViewById(R.id.datestampEditText);

    }

    public void saveSettings(View view)
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("firstName", firstNameET.getText().toString());
        editor.putString("lastname", lastNameET.getText().toString());
        editor.putString("email", emailET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.putString("datestamp", datestampET.getText().toString());
    }

}
