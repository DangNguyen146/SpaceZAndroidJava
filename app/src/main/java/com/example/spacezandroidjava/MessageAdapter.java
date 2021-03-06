package com.example.spacezandroidjava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacezandroidjava.Model.Message;

import java.util.List;

//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
//        private List<String> messageList;
//        private Context context;
//    public MessageAdapter(List<String> messageList,Context context){
//        this.messageList=messageList;
//        this.context=context;
//
//    }
//
//    @NonNull
//    @Override
//    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context=parent.getContext();
//        LayoutInflater inflater=LayoutInflater.from(context);
//        View itemView=inflater.inflate(R.layout.message_item,parent,false);
//        MessageAdapter.ViewHolder viewHolder=new MessageAdapter.ViewHolder(itemView);
//
//        return viewHolder;
//    }
//
//    public void setMessage(List<String> messageList){
//        this.messageList=messageList;
//        notifyDataSetChanged();
//
//    }
//    @Override
//    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
//            String message=messageList.get(position);
//            holder.textView.setText(message);
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageList==null?0:messageList.size();
//    }
//    public static class ViewHolder extends  RecyclerView.ViewHolder{
//        Context context;
//        TextView textView;
//
//        public ViewHolder(@NonNull View itemView){
//            super(itemView);
//            context=itemView.getContext();
//            textView=(TextView) itemView.findViewById(R.id.message);
//
//        }
//    }
//}

public class MessageAdapter extends ArrayAdapter<Message>{
        private Activity context;
        public MessageAdapter(Activity context, int layoutID, List<Message> objects){
            super(context,layoutID,objects);
            this.context=context;

        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, null,
                            false);
        }
        Message message=getItem(position);
        TextView tv_username=(TextView) convertView.findViewById(R.id.sender);
        TextView tv_message=(TextView) convertView.findViewById(R.id.message);
        tv_username.setText(message.getUsername());
        tv_message.setText(message.getText());

        return convertView;
    }
}