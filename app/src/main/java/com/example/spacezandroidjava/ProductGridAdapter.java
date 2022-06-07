package com.example.spacezandroidjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.spacezandroidjava.Model.Product;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;


public class ProductGridAdapter extends BaseAdapter {

    private List<Product> lstProduct;
    private  LayoutInflater layoutInflater;
    private Context context;

    public ProductGridAdapter(Context context, List<Product> lstProduct){
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
//        Glide.with(context).load(product.getImage()).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.productView);
        Picasso.get().load(product.getImage()).resize(500, 340).centerCrop().into(holder.productView);
//            holder.productView.setImageResource(R.drawable.sau_1);
//        holder.productView.setImageBitmap(getBitmapFromURL(product.getImage()));
       return  view;

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    static class ViewHolder {
        ImageView productView;
        TextView nameView;

    }
}
