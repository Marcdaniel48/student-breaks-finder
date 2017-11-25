package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.database.NotesDBHelper;

/**
 * Activity which displays a list of notes stored using SQLite. The user can also add a new note.
 * If a note in the list is longer than 1 line, then the remaining text past the line is cut off, and only available
 * in the ItemNoteActivity.
 *
 * The user can click on a note to open the ItemNoteActivity to see the full note or to delete it.
 */
public class NotesActivity extends Activity
{
    // SQLite database helper class for the notes.
    private static NotesDBHelper notesDBH;

    // Displays the notes.
    private ListView lvNotes;
    private ArrayAdapter<String> adapter;

    EditText etNewNote;

    /**
     * Creates and sets up the activity.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // Gets an instance of NotesDBHelper for accessing the SQLite notes table.
        notesDBH = NotesDBHelper.getNotesDBHelper(this);

        // Assigns lvNotes to the ListView found in activity_notes.xml
        lvNotes = (ListView) findViewById(R.id.listViewNotes);

        // OnClick listener for when a note in the notes list view is clicked on.
        lvNotes.setOnItemClickListener(openNoteOnClick);

        // Updates the notes list view according to the notes stored in the database.
        updateUI();

        // Assigns etNewNote to the EditText field found in activity_notes.xml
        etNewNote = (EditText) findViewById(R.id.newNote);
    }

    /**
     * Method which gives the notes list view its notes and then displays them.
     * Accesses the SQLite database to retrieve any notes stored in the notes table.
     */
    private void updateUI()
    {
        ArrayList<String> noteList = new ArrayList<>();

        SQLiteDatabase db = notesDBH.getReadableDatabase();

        Cursor cursor = db.query(notesDBH.TABLE_NOTES, new String[]{NotesDBHelper.COLUMN_NOTE}, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(notesDBH.COLUMN_NOTE);
            noteList.add(cursor.getString(index));
        }

        if(adapter == null)
        {
            adapter = new ArrayAdapter<String>(this, R.layout.listview_note, R.id.note, noteList);
            lvNotes.setAdapter(adapter);
        }
        else
        {
            adapter.clear();
            adapter.addAll(noteList);
            adapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    /**
     * When this method is called, takes the current String found in the activity's EditText field and
     * adds it to the SQLite database (Assuming the EditText field isn't empty).
     *
     * @param view
     */
    public void addNote(View view)
    {
        if(!etNewNote.getText().toString().equals(""))
        {
            notesDBH.insertNewNote(etNewNote.getText().toString());
            updateUI();

            etNewNote.setText("");
        }
    }

    /**
     * When a note of the notes list is clicked on, launch the ItemNote activity and send a String of the selected note to it.
     */
    private AdapterView.OnItemClickListener openNoteOnClick = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
        {
            String fullNote = (String)adapterView.getItemAtPosition(position);

            Intent i = new Intent(NotesActivity.this, ItemNoteActivity.class);
            i.putExtra("note", fullNote);

            startActivity(i);
        }
    };

    /**
     * When a user leaves the ItemNote activity, refresh the notes list, incase the user deleted a note.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }
}
