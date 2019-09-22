package samidsoft.co.passwordgenerator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samidsoft.co.passwordgenerator.AddNotesActivity;
import samidsoft.co.passwordgenerator.R;
import samidsoft.co.passwordgenerator.database.DatabaseClient;
import samidsoft.co.passwordgenerator.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.CustomView> {

    private List<Note> list;
    private Context context;
    private int position;
    private Note note;
    private String EXTRA = "Update";

    public NotesAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_layout, parent, false);
        return new CustomView(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        note = list.get(position);

        if (note != null) {
            holder.txt_title.setText(context.getResources().getString(R.string.title) + " : " + note.getTitle());
            holder.txt_description.setText(context.getResources().getString(R.string.description) + " : " + note.getDescription());
            holder.txt_date.setText(note.getDateCreation().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CustomView extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        AppCompatTextView txt_title, txt_description, txt_date;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_date = itemView.findViewById(R.id.txt_date);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("Choose Item");
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = item -> {
            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    Intent intent = new Intent(context, AddNotesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note", note);
                    bundle.putString("EXTRA", EXTRA);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    break;

                case 2:
                    //Do stuff
                    deleteNote(note);
                    System.out.println("====delete====");
                    break;
            }
            return false;
        };
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void deleteNote(Note note) {
        class DeleteNotes extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getNoteDatabase().noteDao().deleteNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        DeleteNotes deleteNotes = new DeleteNotes();
        deleteNotes.execute();
    }
}
