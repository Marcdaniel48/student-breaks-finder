package roantrevormarcdanieltiffany.com.dawsonbestfinder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper class.
 * Creates a database and table used for storing notes & Provides CRUD implementation.
 */
public class NotesDBHelper extends SQLiteOpenHelper
{

    // These static final Strings describe the database.
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "note";

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_NOTES + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NOTE + " text not null" + ");";

    // Static instance of NotesDBHelper, for use by other classes.
    private static NotesDBHelper notesDBH = null;

    /**
     * Instantiates the helper object to access the database. Private to prevent instantiation from others.
     * Used in the getNotesDBHelper method.
     * @param context
     */
    private NotesDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Static method that returns the current instance of NotesDBHelper.
     * @param context
     * @return
     */
    public static NotesDBHelper getNotesDBHelper(Context context)
    {
        if(notesDBH == null)
        {
            notesDBH = new NotesDBHelper(context.getApplicationContext());
        }

        return notesDBH;
    }

    /**
     * Create the database.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    /**
     * Override onUpgrade.
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    /**
     * Inserts a note record to the notes table.
     * @param note
     * @return
     */
    public long insertNewNote(String note)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE, note);

        long code = getWritableDatabase().insert(TABLE_NOTES, null, cv);
        return code;
    }

    /**
     * Returns a Cursor with the entries of the notes table.
     * @return
     */
    public Cursor getNotes()
    {
        return getReadableDatabase().query(TABLE_NOTES, null, null, null, null, null, null);
    }

    /**
     * Deletes a note record based on ID.
     * @param id
     * @return
     */
    public int deleteNoteByID(int id)
    {
        return getWritableDatabase().delete(TABLE_NOTES, COLUMN_ID + "=?", new String[] {String.valueOf(id)});
    }

    /**
     * Deletes a note record based on a String of the note.
     * @param note
     * @return
     */
    public int deleteNote(String note)
    {
        return getWritableDatabase().delete(TABLE_NOTES, COLUMN_NOTE + "=?", new String[] {String.valueOf(note)});
    }
}
