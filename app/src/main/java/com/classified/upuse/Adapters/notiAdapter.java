package com.classified.upuse.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classified.upuse.Models.homeResponse;
import com.classified.upuse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class notiAdapter extends RecyclerView.Adapter<notiAdapter.viewHolder> {
    private Context mcontext;
    private List<homeResponse .notiResult> list;
    ontileclick listener;
    public notiAdapter(Context mcontext, List<homeResponse.notiResult> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.noti_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Picasso.get().load(list.get(position).getImage()).into(holder.notiimg);
        Log.d("img",list.get(position).getImage());
        holder.notititle.setText(list.get(position).getTitle());
        holder.notidesc.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView notiimg;
        TextView notititle;
        TextView notidesc;
        View notibg;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            notiimg=itemView.findViewById(R.id.noti_img);
            notititle=itemView.findViewById(R.id.noti_title);
            notidesc=itemView.findViewById(R.id.noti_descr);
            notibg=itemView.findViewById(R.id.noti_card);
            notibg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition()!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(list.get(getAdapterPosition()).getAd_id(),
                                list.get(getAdapterPosition()).getTitle());
                    }
                }
            });
        }
    }
    public interface ontileclick {
        void onItemClick(String ad_id,String prodname);
    }
    public void setontileClick(ontileclick listener) {
        this.listener=listener;
    }
}
