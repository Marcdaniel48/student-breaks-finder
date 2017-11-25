package roantrevormarcdanieltiffany.com.dawsonbestfinder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "note";

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_NOTES + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NOTE + " text not null" + ");";

    private static NotesDBHelper notesDBH = null;

    private NotesDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NotesDBHelper getNotesDBHelper(Context context)
    {
        if(notesDBH == null)
        {
            notesDBH = new NotesDBHelper(context.getApplicationContext());
        }

        return notesDBH;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    public long insertNewNote(String note)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE, note);

        long code = getWritableDatabase().insert(TABLE_NOTES, null, cv);
        return code;
    }

    public Cursor getNotes()
    {
        return getReadableDatabase().query(TABLE_NOTES, null, null, null, null, null, null);
    }

    public int deleteNoteByID(int id)
    {
        return getWritableDatabase().delete(TABLE_NOTES, COLUMN_ID + "=?", new String[] {String.valueOf(id)});
    }

    public int deleteNote(String note)
    {
        return getWritableDatabase().delete(TABLE_NOTES, COLUMN_NOTE + "=?", new String[] {String.valueOf(note)});
    }
}
