package samidsoft.co.passwordgenerator.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import samidsoft.co.passwordgenerator.dao.NoteDao;
import samidsoft.co.passwordgenerator.model.Note;

public class NoteRepository {

    private NoteDao noteDao;
    private List<Note> getAllNotes;

    public NoteRepository(Application application) {
        //NoteDatabase noteDatabase = NoteDatabase.getDatabase(application);
        //noteDao = noteDatabase.noteDao();
        //getAllNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNote((NoteDao) note);
    }

    public void update(Note note) {
        new UpdateNote((NoteDao) note);
    }

    public void delete(Note note) {
        new DeleteNote((NoteDao) note);
    }

    public List<Note> getAllNotes() {
        return getAllNotes;
    }

    class InsertNote extends AsyncTask<Note, Void, Void> {

        NoteDao noteDao;

        public InsertNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    class UpdateNote extends AsyncTask<Note, Void, Void> {

        NoteDao noteDao;

        public UpdateNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    class DeleteNote extends AsyncTask<Note, Void, Void> {

        NoteDao noteDao;

        public DeleteNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }
}


