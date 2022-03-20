package com.example.spacezandroidjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;


public class ProductGridAdapter extends BaseAdapter {

    private List<Product> lstProduct;
    private  LayoutInflater layoutInflater;
    private Context context;
    public ProductGridAdapter(Context context,List<Product> lstProduct){
        this.context=context;
        this.lstProduct=lstProduct;
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return lstProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return lstProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
       if(view==null){
           view=layoutInflater.inflate(R.layout.list_item,null);
            holder=new ViewHolder();
            holder.productView=(ImageView)view.findViewById(R.id.listitemImageView1);
            holder.nameView=(TextView)view.findViewById(R.id.listitemTextView1);
            view.setTag(holder);

       }
       else{
           holder = (ViewHolder) view.getTag();
       }
       Product product=this.lstProduct.get(i);
       holder.nameView.setText(product.getName());
       return  view;

    }


    static class ViewHolder {
        ImageView productView;
        TextView nameView;

    }
}
