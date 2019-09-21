package samidsoft.co.passwordgenerator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import samidsoft.co.passwordgenerator.adapter.NotesAdapter;
import samidsoft.co.passwordgenerator.database.DatabaseClient;
import samidsoft.co.passwordgenerator.model.Note;

public class NotesActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.btn_floating)
    FloatingActionButton btn_floating;

    @Nullable
    @BindView(R.id.input_title1)
    AppCompatEditText input_title1;

    @Nullable
    @BindView(R.id.input_title2)
    AppCompatEditText input_title2;

    @Nullable
    @BindView(R.id.btn_save)
    AppCompatButton save;

    @Nullable
    @BindView(R.id.btn_discard)
    AppCompatButton discard;

    @Nullable
    @BindView(R.id.list_item)
    RecyclerView recyclerView;

    @Optional
    @OnClick(R.id.btn_floating)
    void getDialog() {
        Toast.makeText(this, getResources().getString(R.string.password_copied), Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllData();
    }

    public void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_notes_layout_dialog, null);
        dialog.setContentView(view);
        ButterKnife.bind(this, dialog);

        save.setOnClickListener(view1 -> {
            String title = input_title1.getText().toString();
            String description = input_title2.getText().toString();
            System.out.println("===--====" + title + "  " + description);

            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setDateCreation(new Date());
            runTask(note);

            dialog.dismiss();
        });

        discard.setOnClickListener(view12 -> dialog.dismiss());

        dialog.show();
    }

    public void runTask(Note note) {

        class AddNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(NotesActivity.this).getNoteDatabase().noteDao().insertNote(note);
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

    public void getAllData() {
        class GetAllTask extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                List<Note> list = DatabaseClient.getInstance(NotesActivity.this).getNoteDatabase().noteDao().getAllNotes();
                if (list != null) {
                    NotesAdapter notesAdapter = new NotesAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(notesAdapter);
                    System.out.println("getItemCount===" + notesAdapter.getItemCount());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        GetAllTask getAllTask = new GetAllTask();
        getAllTask.execute();
    }
}
