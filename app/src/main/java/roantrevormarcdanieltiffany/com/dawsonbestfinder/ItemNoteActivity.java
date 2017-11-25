package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import roantrevormarcdanieltiffany.com.dawsonbestfinder.database.NotesDBHelper;

public class ItemNoteActivity extends Activity
{

    TextView tvFullNote;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemnotes);

        tvFullNote = (TextView) findViewById(R.id.tvItemNote);

        if(getIntent().getExtras().containsKey("note"))
        {
            String note = getIntent().getStringExtra("note");
            tvFullNote.setText(note);
        }
    }

    public void deleteNote(View view)
    {
        NotesDBHelper notesDBH = NotesDBHelper.getNotesDBHelper(this);

        notesDBH.deleteNote(tvFullNote.getText().toString());

        finish();
    }

}
