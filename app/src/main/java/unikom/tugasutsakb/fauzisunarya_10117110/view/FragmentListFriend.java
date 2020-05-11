package unikom.tugasutsakb.fauzisunarya_10117110.view;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import unikom.tugasutsakb.fauzisunarya_10117110.MainActivity;
import unikom.tugasutsakb.fauzisunarya_10117110.R;
import unikom.tugasutsakb.fauzisunarya_10117110.model.Friend;
import unikom.tugasutsakb.fauzisunarya_10117110.presenter.FriendAdapter;
import unikom.tugasutsakb.fauzisunarya_10117110.presenter.FriendHelper;
import unikom.tugasutsakb.fauzisunarya_10117110.presenter.MapingHelper;


/** NIM : 10117110
 * Nama : Fauzi Sunarya
 * Kelas : IF-3
 * Tanggal : 08-05-2020**/


public class FragmentListFriend extends Fragment implements LoadFriendCallback {

    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private FriendAdapter adapter;
    private FriendHelper friendHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FloatingActionButton fabAdd;

//    private ImageView fabAdd;

    public FragmentListFriend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_friend, container, false);



//        if ( ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Friends");
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03AC0E")));

            progressBar = view.findViewById(R.id.progressbar);
            rvNotes = view.findViewById(R.id.rv_notes);
            rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvNotes.setHasFixedSize(true);
            adapter = new FriendAdapter(getActivity());
            rvNotes.setAdapter(adapter);

            fabAdd = view.findViewById(R.id.fab_add);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FriendAddUpdateActivity.class);
                    startActivityForResult(intent, FriendAddUpdateActivity.REQUEST_ADD);
                }
            });
//        }
        friendHelper = FriendHelper.getInstance(getActivity());
        friendHelper.open();

        if (savedInstanceState == null) {
            new LoadNotesAsync(friendHelper, this).execute();
        } else {
            ArrayList<Friend> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListNotes(list);
            }
        }


        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }


    @Override
    public void preExecute() {
        new Runnable(){
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void postExecute(ArrayList<Friend> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<Friend>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Friend>> {
        private final WeakReference<FriendHelper> weakNoteHelper;
        private final WeakReference<LoadFriendCallback> weakCallback;

        private LoadNotesAsync(FriendHelper noteHelper, LoadFriendCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Friend> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queryAll();
            return MapingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Friend> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == FriendAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == FriendAddUpdateActivity.RESULT_ADD) {
                    Friend note = data.getParcelableExtra(FriendAddUpdateActivity.EXTRA_NOTE);

                    adapter.addItem(note);
                    rvNotes.smoothScrollToPosition(adapter.getItemCount() - 1);

                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            } else if (requestCode == FriendAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == FriendAddUpdateActivity.RESULT_UPDATE) {
                    Friend note = data.getParcelableExtra(FriendAddUpdateActivity.EXTRA_NOTE);
                    int position = data.getIntExtra(FriendAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.updateItem(position, note);
                    rvNotes.smoothScrollToPosition(position);

                    showSnackbarMessage("Satu item berhasil dirubah");
                } else if (resultCode == FriendAddUpdateActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(FriendAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);

                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        friendHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadFriendCallback{
    void preExecute();
    void postExecute(ArrayList<Friend> notes);
}



