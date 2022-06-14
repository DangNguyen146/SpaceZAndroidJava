package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacezandroidjava.Model.Contact;
import com.example.spacezandroidjava.Model.GetCommnetsResponse;
import com.example.spacezandroidjava.Model.UserComment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<GetCommnetsResponse> lstComments;
    private Context context;

    public  CommentAdapter(Context context,List<GetCommnetsResponse> lstComments){
        this.context=context;
        this.lstComments=lstComments;

    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.comment_item,parent,false);
        CommentAdapter.ViewHolder viewHolder=new CommentAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        GetCommnetsResponse comment=lstComments.get(position);
         UserComment user=comment.getUser();
        String content=comment.getContent();
        String username=user.getUsername();
        String avatarUrl=user.getAvartar();
        Picasso.get().load(avatarUrl).into(holder.userAvatar);
        holder.commentUsername.setText(username);
         holder.commentContent.setText(content);




    }

    @Override
    public int getItemCount() {
        return lstComments==null?0:lstComments.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentUsername;

        ImageView userAvatar;
        Context context;
        TextView commentContent;







        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            commentUsername=(TextView) itemView.findViewById(R.id.comment_username);
            userAvatar=(ImageView) itemView.findViewById(R.id.comment_avatar);
            commentContent=(TextView) itemView.findViewById(R.id.comment_content);

//            itemView.setOnClickListener(this);
//            name=itemView.findViewById(R.id.card_name_cart);
//            price=itemView.findViewById(R.id.card_price_cart);
//            eB=itemView.findViewById(R.id.amount_btn);



        }



    }
}
