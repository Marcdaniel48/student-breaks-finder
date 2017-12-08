package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * SettingsActivity.class
 *
 * Class that handles the logic for the settings activity.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class SettingsActivity extends Activity
{
    EditText etFirstName, etLastName, etEmail, etPassword, etDatestamp;
    TextView tvValidationMessage;

    // For retrieving data from Settings SharedPreferences.
    protected static final String FIRST_NAME = "firstName";
    protected static final String LAST_NAME = "lastName";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String DATESTAMP = "datestamp";

    // Name for Settings SharedPreferences
    protected static final String SETTINGS = "settings";

    // For storing and setting validation message and text color using saved instance state
    protected static final String VALIDATION_MESSAGE = "validationMessage";
    protected static final String VALIDATION_MESSAGE_COLOR = "validationMessageColor";

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
        tvValidationMessage = findViewById(R.id.validationMessageTextView);

        // Restores state for validation message and validation message text color
        if(savedInstanceState != null && savedInstanceState.getString(VALIDATION_MESSAGE) != null && savedInstanceState.getInt(VALIDATION_MESSAGE_COLOR) != 0)
        {
            tvValidationMessage.setTextColor(savedInstanceState.getInt(VALIDATION_MESSAGE_COLOR));
            tvValidationMessage.setText(savedInstanceState.getString(VALIDATION_MESSAGE));
        }

        SharedPreferences prefs = getSharedPreferences(SETTINGS,MODE_PRIVATE);

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
     * Saves user information.
     * Takes the values found in the EditText fields and stores them in SharedPreferences.
     * @param view
     */
    public void saveSettings(View view)
    {
        if(!emptyInputFields())
        {
            if(isEmail(etEmail.getText().toString())) {
                SharedPreferences prefs = getSharedPreferences(SETTINGS,MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString(FIRST_NAME, etFirstName.getText().toString());
                editor.putString(LAST_NAME, etLastName.getText().toString());
                editor.putString(EMAIL, etEmail.getText().toString());
                editor.putString(PASSWORD, etPassword.getText().toString());

                Date date = new Date();
                etDatestamp.setText(date.toString());
                editor.putString(DATESTAMP, etDatestamp.getText().toString());
                editor.commit();

                tvValidationMessage.setTextColor(getResources().getColor(R.color.color_black));
                tvValidationMessage.setText(getResources().getString(R.string.settings_validation_saved));
            }
            // If the entered email is invalid, warn the user and don't save.
            else
            {
                tvValidationMessage.setTextColor(getResources().getColor(R.color.color_red));
                tvValidationMessage.setText(getResources().getString(R.string.settings_validation_invalidEmail));
            }
        }
        // If any input fields are empty, warn the user and don't save.
        else
        {
            tvValidationMessage.setTextColor(getResources().getColor(R.color.color_red));
            tvValidationMessage.setText(getResources().getString(R.string.settings_validation_emptyfields));
        }
    }

    /**
     * When the back button is pressed, an alert dialog will appear asking for exit confirmation.
     */
    @Override
    public void onBackPressed()
    {
        SharedPreferences prefs = getSharedPreferences(SETTINGS,MODE_PRIVATE);

        if(prefs.contains(FIRST_NAME) && prefs.contains(LAST_NAME) && prefs.contains(EMAIL) && prefs.contains(PASSWORD))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton(R.string.settings_back_dialog_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SettingsActivity.super.onBackPressed();
                }
            });

            builder.setNegativeButton(R.string.settings_back_dialog_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.setMessage(R.string.settings_back_dialog_message).setTitle(R.string.settings_back_dialog_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        // The user cannot leave the settings activity to go to the main menu, if he hasn't successfully entered and saved user settings.
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setNegativeButton(R.string.settings_back_dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.setMessage(R.string.settings_back_dialog_message_no_input).setTitle(R.string.settings_back_dialog_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * Checks if any of the EditText fields for the user information are empty. Return true if there is an empty field.
     *
     * @return
     */
    public boolean emptyInputFields()
    {

        if(
                etFirstName.getText().toString().trim().isEmpty() ||
                etLastName.getText().toString().trim().isEmpty() ||
                etEmail.getText().toString().trim().isEmpty() ||
                etPassword.getText().toString().trim().isEmpty())
            return true;

        return false;
    }

    /**
     * Takes in a String and checks if it is a valid email. If it isn't, return false.
     * @param email
     * @return
     */
    public boolean isEmail(String email)
    {
        // Regex string adapted from an answer in https://stackoverflow.com/questions/8204680/java-regex-email
        // by Jason Buberel --> https://stackoverflow.com/users/202275/jason-buberel
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        return email.matches(emailRegex);
    }

    /**
     * Puts the current validation message and its text color into saved instance state.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString(VALIDATION_MESSAGE, tvValidationMessage.getText().toString());
        outState.putInt(VALIDATION_MESSAGE_COLOR, tvValidationMessage.getCurrentTextColor());
    }

}
