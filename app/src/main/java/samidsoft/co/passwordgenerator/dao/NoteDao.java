package samidsoft.co.passwordgenerator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import samidsoft.co.passwordgenerator.model.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :key")
    Note getNotesByKey(Long key);

    @Query("SELECT * FROM note WHERE title LIKE '%'|| :key || '%'")
    List<Note> getNotesByKeyWord(String key);

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
