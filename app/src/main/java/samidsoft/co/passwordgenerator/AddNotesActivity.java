package samidsoft.co.passwordgenerator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import samidsoft.co.passwordgenerator.database.DatabaseClient;
import samidsoft.co.passwordgenerator.model.Note;

public class AddNotesActivity extends AppCompatActivity {

    @BindView(R.id.input_title1)
    AppCompatEditText text1;

    @BindView(R.id.input_title2)
    AppCompatEditText text2;

    @BindView(R.id.btn_save)
    AppCompatButton btn_save;

    @BindView(R.id.btn_discard)
    AppCompatButton btn_discard;

    @OnClick(R.id.btn_save)
    void saveNotes() {
        Note note = new Note();
        note.setTitle(text1.getText().toString());
        note.setDescription(text2.getText().toString());
        note.setDateCreation(new Date());
        runTask(note);
        finish();
    }

    @OnClick(R.id.btn_discard)
    void done() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Note note = (Note) bundle.get("note");
            if (note != null) {
                text1.setText(note.getTitle());
                text2.setText(note.getDescription());
            }

            System.out.println("EXTRA====" + bundle.getString("EXTRA"));
        }
    }


    public void updateNote(Note note) {
        class UpdateNotes extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(AddNotesActivity.this).getNoteDatabase().noteDao().updateNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_LONG).show();
            }
        }

        UpdateNotes updateNotes = new UpdateNotes();
        updateNotes.execute();
    }


    public void runTask(Note note) {

        class AddNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(AddNotesActivity.this).getNoteDatabase().noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        AddNote addNote = new AddNote();
        addNote.execute();
    }
}
