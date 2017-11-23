package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

/**
 * SettingsActivity.class
 *
 * Class that handles the logic for the settings activity.
 */
public class SettingsActivity extends Activity
{
    EditText etFirstName, etLastName, etEmail, etPassword, etDatestamp;
    protected static final String FIRST_NAME = "firstName";
    protected static final String LAST_NAME = "lastName";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String DATESTAMP = "datestamp";

    /**
     * When the activity is created, call super.onCreate, then look into SharedPreferences for saved user information.
     * If SharedPreferences contain user information values, set those values into the activity's EditText fields.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        etFirstName = findViewById(R.id.firstNameEditText);
        etLastName = findViewById(R.id.lastNameEditText);
        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etDatestamp = findViewById(R.id.datestampEditText);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        if(prefs.contains(FIRST_NAME))
            etFirstName.setText(prefs.getString(FIRST_NAME, ""));

        if(prefs.contains(LAST_NAME))
            etLastName.setText(prefs.getString(LAST_NAME,""));

        if(prefs.contains(EMAIL))
            etEmail.setText(prefs.getString(EMAIL, ""));

        if(prefs.contains(PASSWORD))
            etPassword.setText(prefs.getString(PASSWORD, ""));

        if(prefs.contains(DATESTAMP))
            etDatestamp.setText(prefs.getString(DATESTAMP, ""));
    }

    /**
     * Takes the values found in the EditText fields and stores them in SharedPreferences.
     * @param view
     */
    public void saveSettings(View view)
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(FIRST_NAME, etFirstName.getText().toString());
        editor.putString(LAST_NAME, etLastName.getText().toString());
        editor.putString(EMAIL, etEmail.getText().toString());
        editor.putString(PASSWORD, etPassword.getText().toString());

        Date date = new Date();
        etDatestamp.setText(date.toString());
        editor.putString(DATESTAMP, etDatestamp.getText().toString());
        editor.apply();
    }

    /**
     * When the back button is pressed, an alert dialog will appear asking for exit confirmation.
     */
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
