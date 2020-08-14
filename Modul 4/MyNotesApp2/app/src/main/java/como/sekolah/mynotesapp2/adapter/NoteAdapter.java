package como.sekolah.mynotesapp2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.mynotesapp2.CustomOnItemClickListener;
import como.sekolah.mynotesapp2.NoteAddUpdateActivity;
import como.sekolah.mynotesapp2.R;
import como.sekolah.mynotesapp2.entity.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> listNotes = new ArrayList<>();
    private Activity activity;

//    constractor
    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

//    getter
    public ArrayList<Note> getListNotes() {
        return listNotes;
    }

//    setter
    public void setListNotes(ArrayList<Note> listNotes) {
        if (listNotes.size() > 0) {
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);
        notifyDataSetChanged();
    }

//    method 1
    public void addItem(Note note) {
        this.listNotes.add(note);
        notifyItemInserted(listNotes.size());
    }

//    mrthod 2
    public void updateItem(int position, Note note) {
        this.listNotes.set(position, note);
        notifyDataSetChanged();
    }

//    method 3
    public void removeItem(int postion) {
        this.listNotes.remove(postion);
        notifyItemRemoved(postion);
        notifyItemRangeChanged(postion, listNotes.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvTitle.setText(listNotes.get(position).getTitle());
        holder.tvDate.setText(listNotes.get(position).getDate());
        holder.tvDesctiption.setText(listNotes.get(position).getDescription());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void OnItemClicked(View view, int position) {
                Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNotes.get(position));
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDesctiption, tvDate;
        final CardView cvNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDesctiption = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
