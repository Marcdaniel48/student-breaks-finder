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

        etFirstName = (EditText) findViewById(R.id.firstNameEditText);
        etLastName = (EditText) findViewById(R.id.lastNameEditText);
        etEmail = (EditText) findViewById(R.id.emailEditText);
        etPassword = (EditText) findViewById(R.id.passwordEditText);
        etDatestamp = (EditText) findViewById(R.id.datestampEditText);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        if(prefs.contains("firstName"))
            etFirstName.setText(prefs.getString("firstName", ""));

        if(prefs.contains("lastName"))
            etLastName.setText(prefs.getString("lastName",""));

        if(prefs.contains("email"))
            etEmail.setText(prefs.getString("email", ""));

        if(prefs.contains("password"))
            etPassword.setText(prefs.getString("password", ""));

        if(prefs.contains("datestamp"))
            etDatestamp.setText(prefs.getString("datestamp", ""));
    }

    /**
     * Takes the values found in the EditText fields and stores them in SharedPreferences.
     * @param view
     */
    public void saveSettings(View view)
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("firstName", etFirstName.getText().toString());
        editor.putString("lastName", etLastName.getText().toString());
        editor.putString("email", etEmail.getText().toString());
        editor.putString("password", etPassword.getText().toString());

        Date date = new Date();
        etDatestamp.setText(date.toString());
        editor.putString("datestamp", etDatestamp.getText().toString());
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
