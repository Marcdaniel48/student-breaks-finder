package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.database.NotesDBHelper;

/**
 * Displays a complete note and allows the user to delete it, through a button.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class ItemNoteActivity extends Activity
{
    TextView tvFullNote;

    /**
     * Creates and sets up the ItemNote activity.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemnotes);

        tvFullNote = findViewById(R.id.tvItemNote);

        /**
         * This activity is only opened through the Notes activity.
         * Gets the String that the Notes activity sent and displays it.
         */
        if(getIntent().getExtras().containsKey("note"))
        {
            String note = getIntent().getStringExtra("note");
            tvFullNote.setText(note);
        }
    }

    /**
     * Deletes the currently displaying note and exits the activity.
     * @param view
     */
    public void deleteNote(View view)
    {
        NotesDBHelper notesDBH = NotesDBHelper.getNotesDBHelper(this);

        notesDBH.deleteNote(tvFullNote.getText().toString());

        finish();
    }

}
