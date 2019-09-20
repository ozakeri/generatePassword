package samidsoft.co.passwordgenerator;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import samidsoft.co.passwordgenerator.database.NoteRepository;
import samidsoft.co.passwordgenerator.model.Note;

public class NotesActivity extends AppCompatActivity {
    @BindView(R.id.btn_floating)
    FloatingActionButton btn_floating;

    @BindView(R.id.input_title1)
    AppCompatEditText input_title1;

    @BindView(R.id.input_title2)
    AppCompatEditText input_title2;

    @BindView(R.id.btn_save)
    AppCompatButton save;

    @BindView(R.id.btn_discard)
    AppCompatButton discard;

    @OnClick(R.id.btn_floating)
    void onClick() {
        Toast.makeText(this, getResources().getString(R.string.password_copied), Toast.LENGTH_SHORT).show();
        //NoteRepository noteRepository = new NoteRepository(getApplication());
        //showDialog(noteRepository);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

       /* btn_floating.setOnClickListener(view -> {
            NoteRepository noteRepository = new NoteRepository(getApplication());
            showDialog(noteRepository);
        });*/
    }

    public void showDialog(NoteRepository noteRepository) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // LayoutInflater inflater = this.getLayoutInflater();
        //View view = inflater.inflate(R.layout.add_notes_layout_dialog, null);
        dialog.setContentView(R.layout.add_notes_layout_dialog);

        String title = input_title1.getText().toString();
        String description = input_title2.getText().toString();

        save.setOnClickListener(view1 -> {
            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setDateCreation(new Date());
            addNote(noteRepository, note);

        });

        discard.setOnClickListener(view12 -> dialog.dismiss());

        dialog.show();
    }

    private static void addNote(NoteRepository noteRepository, Note note) {
        noteRepository.insert(note);
    }
}
