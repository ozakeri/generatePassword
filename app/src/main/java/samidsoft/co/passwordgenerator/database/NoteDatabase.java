package samidsoft.co.passwordgenerator.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import samidsoft.co.passwordgenerator.dao.NoteDao;
import samidsoft.co.passwordgenerator.model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
}
