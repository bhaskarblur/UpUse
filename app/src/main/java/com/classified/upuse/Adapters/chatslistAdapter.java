package com.classified.upuse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classified.upuse.Models.chatModel;

import com.classified.upuse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class chatslistAdapter extends RecyclerView.Adapter<chatslistAdapter.viewHolder> {

    private Context mcontext;
    private List<chatModel.chatResult> list;
    onItemClick listener;

    public chatslistAdapter(Context mcontext, List<chatModel.chatResult> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.chat_msg_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(list.get(position).getPerson_name());
        holder.prod_name.setText(list.get(position).getProduct_name());
        if(list.get(position).getPerson_img()!=null) {
            Picasso.get().load(list.get(position).getPerson_img()).resize(150, 150)
                    .transform(new CropCircleTransformation()).centerCrop().into(holder.img);
        }
        holder.lastmsg.setText(list.get(position).getRecent_msg());

        if(list.get(position).getRecent_msg_count()!=null && !list.get(position).getRecent_msg_count()
        .equals("0")) {

            holder.msgcount.setText(list.get(position).getRecent_msg_count());
        }
        else {
            holder.msgcount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        View card;
        ImageView img;
        TextView name;
        TextView lastmsg;
        TextView prod_name;
        View msgcount_lay;
        TextView msgcount;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.chatbg);
            img=itemView.findViewById(R.id.person_pic);
            name=itemView.findViewById(R.id.person_name);
            prod_name=itemView.findViewById(R.id.prod_name);
            lastmsg=itemView.findViewById(R.id.recent_msg);
            msgcount=itemView.findViewById(R.id.recentmsg_count);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=RecyclerView.NO_POSITION) {
                        listener.ontileClick(list.get(getAdapterPosition()).getUser_id(),
                                list.get(getAdapterPosition()).getPerson_id(),
                                list.get(getAdapterPosition()).getProduct_id());
                    }
                }
            });
        }
    }

    public interface onItemClick {
        void ontileClick(String userid,String person_id,String product_id);

    }

    public void setonItemclick(onItemClick listener) {
        this.listener=listener;
    }
}
