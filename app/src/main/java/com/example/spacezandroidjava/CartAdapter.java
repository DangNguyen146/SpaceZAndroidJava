package com.example.spacezandroidjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacezandroidjava.Model.Cart;
import com.example.spacezandroidjava.Model.CartProduct;
import com.example.spacezandroidjava.Model.Product;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.List;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> listProduct;
    private Context context;

    //    private List<Product> listProduct;
    public CartAdapter(List<Cart>listProduct,Context context){
        this.listProduct=listProduct;
        this.context=context;


    }

    public void removeItem(int pos){
        listProduct.remove(pos);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.card,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        return  viewHolder;




    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart=listProduct.get(position);
        Gson g=new Gson();
        Object product=cart.getProduct();
        CartProduct cP=g.fromJson(product.toString(),CartProduct.class);
        holder.name.setText(cP.getName());
        holder.price.setText("Giá:"+ Integer.toString(cP.getPrice()));
        holder.itemView.setTag(cP.getId());

    }


    @Override
    public int getItemCount() {
        return listProduct==null?0: listProduct.size();
    }


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View viewProduct;
//
//        TextView name;
//        TextView price;
//        if(view==null){
//            viewProduct=View.inflate(viewGroup.getContext(),R.layout.card,null);
//        }
//        else{
//            viewProduct=view;
//        }
//        Cart cart=(Cart)getItem(i);
//        Gson g=new Gson();
//        Object product=cart.getProduct();
//        CartProduct cP=g.fromJson(product.toString(),CartProduct.class);
//
////    Product p=(Product) getItem(i);
//
//
//
//
//
//
//        name= (TextView) viewProduct.findViewById(R.id.card_name_cart);
//        price=(TextView) viewProduct.findViewById(R.id.card_price_cart);
//        name.setText(cP.getName());
//        price.setText("Giá "+ Integer.toString(cP.getPrice()));
//
//        return viewProduct;
//    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        int id;
        int cartLength;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.card_name_cart);
            price=itemView.findViewById(R.id.card_price_cart);

        }
    }
}
