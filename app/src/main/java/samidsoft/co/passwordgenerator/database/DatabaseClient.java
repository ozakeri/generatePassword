package samidsoft.co.passwordgenerator.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private NoteDatabase noteDatabase;
    private static DatabaseClient INSTANCE;

    public DatabaseClient(Context context) {
        this.context = context;
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "notes").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseClient(context);
        }
        return INSTANCE;
    }

    public NoteDatabase getNoteDatabase() {
        return noteDatabase;
    }
}
