package samidsoft.co.passwordgenerator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import samidsoft.co.passwordgenerator.model.Note;

@Dao
public interface NoteDao {

    @Insert
    Long insertNote(Note note);

    @Query("SELECT * FROM Note ORDER BY dateCreation ASC")
    LiveData<List<Note>> getAllNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
