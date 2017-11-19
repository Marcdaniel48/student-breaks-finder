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

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        if(prefs.contains("firstName"))
            firstNameET.setText(prefs.getString("firstName", null));

        if(prefs.contains("lastName"))
            lastNameET.setText(prefs.getString("lastName",null));

        if(prefs.contains("email"))
            emailET.setText(prefs.getString("email", null));

        if(prefs.contains("password"))
            passwordET.setText(prefs.getString("password", null));

        if(prefs.contains("datestamp"))
            datestampET.setText(prefs.getString("datestamp", null));
    }

    public void saveSettings(View view)
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("firstName", firstNameET.getText().toString());
        editor.putString("lastName", lastNameET.getText().toString());
        editor.putString("email", emailET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.putString("datestamp", datestampET.getText().toString());
    }

}
