package como.sekolah.mynotesapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import como.sekolah.mynotesapp2.adapter.NoteAdapter;
import como.sekolah.mynotesapp2.db.NoteHelper;
import como.sekolah.mynotesapp2.entity.Note;
import como.sekolah.mynotesapp2.helper.MappingHelper;

public class MainActivity extends AppCompatActivity implements LoadNotesCallback{

    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private NoteAdapter adapter;

    private FloatingActionButton fabAdd;

    private NoteHelper noteHelper;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Logic 1
        if (getActionBar() != null) {
            getActionBar().setTitle("Notes");
        }

        progressBar = findViewById(R.id.progressbar);
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);
        adapter = new NoteAdapter(this);
        rvNotes.setAdapter(adapter);

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
                startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD);
            }
        });

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

//        Logic 2
        if (savedInstanceState == null) {
//            proses ambil data
            new LoadNotesAsync(noteHelper, this).execute();
        } else {
            ArrayList<Note> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListNotes(list);
            }
        }
    }

//    method 1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            // Akan dipanggil jika request codenya ADD
            if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == NoteAddUpdateActivity.RESULT_ADD) {
                    Note note = data.getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);

                    adapter.addItem(note);
                    rvNotes.smoothScrollToPosition(adapter.getItemCount() - 1);

                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            }
            // Update dan Delete memiliki request code sama akan tetapi result codenya berbeda
            else if (requestCode == NoteAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {

                    Note note = data.getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);
                    int position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.updateItem(position, note);
                    rvNotes.smoothScrollToPosition(position);

                    showSnackbarMessage("Satu item berhasil di ubah");
                } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);

                    showSnackbarMessage("Satu item berhasil di hapus");
                }
            }
        }
    }

//    method 2
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }

//    method 3
    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteHelper.close();
    }

//    methood 4
    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

//    mrthod 5
    @Override
    public void postExecute(ArrayList<Note> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<Note>());
            showSnackbarMessage("Tidak Ada Data saat ini");
        }
    }

//    method 6
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }

//    class 2
    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Note>> {

        private final WeakReference<NoteHelper> weakNoteHelper;
        private final WeakReference<LoadNotesCallback> weakCallback;

//        method 1
        private LoadNotesAsync(NoteHelper noteHelper, LoadNotesCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

//        method 2
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

//        method 3
        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queyAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

//        method 4
        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);
        }
    }
}

//inteface
interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Note> notes);
}
