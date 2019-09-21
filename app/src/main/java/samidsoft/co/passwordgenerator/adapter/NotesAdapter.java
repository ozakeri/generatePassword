package samidsoft.co.passwordgenerator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samidsoft.co.passwordgenerator.R;
import samidsoft.co.passwordgenerator.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.CustomView> {

    private List<Note> list;
    private Context context;

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

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        Note note = list.get(position);

        //if (note != null) {
            System.out.println("=======-----" + note.getTitle());
            System.out.println("=======-----" + note.getDateCreation());
            System.out.println("=======-----" + note.getDescription());
            holder.txt_title.setText(note.getTitle());
            holder.txt_description.setText(note.getDescription());
        //}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomView extends RecyclerView.ViewHolder {
        AppCompatTextView txt_title, txt_description;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
        }
    }
}
