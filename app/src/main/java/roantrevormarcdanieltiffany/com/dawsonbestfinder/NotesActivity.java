package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
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

public class NotesActivity extends Activity
{
    private static NotesDBHelper notesDBH;

    private ListView lvNotes;
    private ArrayAdapter<String> adapter;

    EditText etNewNote;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesDBH = NotesDBHelper.getNotesDBHelper(this);
        lvNotes = (ListView) findViewById(R.id.listViewNotes);
        lvNotes.setOnItemClickListener(openQuoteOnClick);
        updateUI();
        etNewNote = (EditText) findViewById(R.id.newNote);
    }

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
            adapter = new ArrayAdapter<String>(this, R.layout.item_note, R.id.note, noteList);
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

    public void addNote(View view)
    {
        if(!etNewNote.getText().toString().equals(""))
        {
            notesDBH.insertNewNote(etNewNote.getText().toString());
            updateUI();
        }
    }

    private AdapterView.OnItemClickListener openQuoteOnClick = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
        {
            
        }
    };
}
