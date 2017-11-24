package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.database.NotesDBHelper;

public class NotesActivity extends Activity
{
    private static NotesDBHelper notesDBH;

    private ListView lvNotes;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesDBH = NotesDBHelper.getNotesDBHelper(this);

        lvNotes = (ListView) findViewById(R.id.listViewNotes);
    }

    private void updateUI()
    {
        ArrayList<String> noteList = new ArrayList<>();

        SQLiteDatabase db = notesDBH.getReadableDatabase();
        Cursor cursor = db.query(notesDBH.TABLE_NOTES, new String[]{NotesDBHelper.COLUMN_NOTE}, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            noteList.add(cursor.getString(cursor.getColumnIndex(notesDBH.COLUMN_NOTE)));
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
}
