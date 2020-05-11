package unikom.tugasutsakb.fauzisunarya_10117110.presenter;

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

import unikom.tugasutsakb.fauzisunarya_10117110.R;
import unikom.tugasutsakb.fauzisunarya_10117110.model.Friend;
import unikom.tugasutsakb.fauzisunarya_10117110.view.FriendAddUpdateActivity;

/** NIM : 10117110
 * Nama : Fauzi Sunarya
 * Kelas : IF-3
 * Tanggal : 08-05-2020**/

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private ArrayList<Friend> listFriend = new ArrayList<>();
    private Activity activity;

    public FriendAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<Friend> getListNotes(){
        return listFriend;
    }

    public void setListNotes(ArrayList<Friend> listNotes){
        if (listNotes.size() > 0 ){
            this.listFriend.clear();
        }
        this.listFriend.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void addItem(Friend note){
        this.listFriend.add(note);
        notifyItemInserted(listFriend.size() -1);
    }

    public void updateItem(int position, Friend note){
        this.listFriend.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position){
        this.listFriend.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFriend.size());
    }

    @NonNull
    @Override
    public FriendAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.FriendViewHolder holder, int position) {
        holder.tvNim.setText(listFriend.get(position).getNim());
        holder.tvNama.setText(listFriend.get(position).getNama());
        holder.tvKelas.setText(listFriend.get(position).getKelas());
        holder.tvTelp.setText(listFriend.get(position).getTelpon());
        holder.tvEmail.setText(listFriend.get(position).getEmail());
        holder.tvIg.setText(listFriend.get(position).getIg());
        holder.tvDate.setText(listFriend.get(position).getDate());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FriendAddUpdateActivity.class);
                intent.putExtra(FriendAddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(FriendAddUpdateActivity.EXTRA_NOTE, listFriend.get(position));
                activity.startActivityForResult(intent, FriendAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder{
        final TextView tvNim, tvNama, tvKelas, tvTelp, tvEmail, tvIg, tvDate;
        final CardView cvNote;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvNama = itemView.findViewById(R.id.tv_item_nama);
            tvKelas = itemView.findViewById(R.id.tv_item_kelas);
            tvTelp = itemView.findViewById(R.id.tv_item_telpon);
            tvEmail = itemView.findViewById(R.id.tv_item_email);
            tvIg = itemView.findViewById(R.id.tv_item_ig);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
