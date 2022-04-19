package com.example.spacezandroidjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
        private List<String> messageList;
        private Context context;
    public MessageAdapter(List<String> messageList,Context context){
        this.messageList=messageList;
        this.context=context;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.message_item,parent,false);
        MessageAdapter.ViewHolder viewHolder=new MessageAdapter.ViewHolder(itemView);

        return viewHolder;
    }

    public void setMessage(List<String> messageList){
        this.messageList=messageList;
        notifyDataSetChanged();

    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
            String message=messageList.get(position);
            holder.textView.setText(message);
    }

    @Override
    public int getItemCount() {
        return messageList==null?0:messageList.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        Context context;
        TextView textView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            context=itemView.getContext();
            textView=(TextView) itemView.findViewById(R.id.message);

        }
    }
}
