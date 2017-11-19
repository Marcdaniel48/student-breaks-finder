package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.settings_back_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                SettingsActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton(R.string.settings_back_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });

        builder.setMessage(R.string.settings_back_dialog_message).setTitle(R.string.settings_back_dialog_title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
