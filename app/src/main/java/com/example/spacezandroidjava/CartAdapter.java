package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.spacezandroidjava.Model.AdjustAmount;
import com.example.spacezandroidjava.Model.Cart;
import com.example.spacezandroidjava.Model.Product;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> listProduct;
    private Context context;
    int newTotal;
    TextView tv_number;

    //    private List<Product> listProduct;
    public CartAdapter(List<Cart>listProduct,Context context,TextView tv_number){
        this.listProduct=listProduct;
        this.context=context;
        this.newTotal=0;
        this.tv_number=tv_number;

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

        Product product=cart.getProduct();
        holder.name.setText(product.getName());

        holder.price.setText("Giá:"+ product.getPrice().toString());
        holder.itemView.setTag(product.getId());
        holder.eB.setNumber(Integer.toString(cart.getAmount()) );
        Picasso.get().load(product.getImage()).into(holder.productImage);
        holder.eB.setOnValueChangeListener(
                new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        AdjustAmount adjustAmount=new AdjustAmount(cart.getIdCart(),cart.getIdProduct(),newValue);
                        Call<Object> message=ApiClient.getService().adjustAmount(adjustAmount);
                        message.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.i("dfs", "onResponse: ");
//                              int pos= holder.getAdapterPosition();
                                changeAmount(holder.getAdapterPosition(),newValue);

//                                holder.tv_total.setText(Integer.toString(calculateTotal()));
                            newTotal=calculateTotal();
                            tv_number.setText((Integer.toString(newTotal))+" đ");



                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.i("failure", "onFailure: ");
                            }
                        });
                    }
                }
        );

        holder.p=product;
        holder.c=cart;

    }
    public void changeAmount(int pos,int newValue){
        listProduct.get(pos).setAmount(newValue);

    }
    public  int calculateTotal(){
        int total=0;
       for (int i=0;i<listProduct.size();i++){
           total+= listProduct.get(i).getProduct().getPrice()*listProduct.get(i).getAmount();
       }
       return total;
    }

    @Override
    public int getItemCount() {
        return listProduct==null?0: listProduct.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView price;
        Context context;
        ImageView productImage;

        Product p;
        ElegantNumberButton eB;
        Cart c;
        int id;
        int cartLength;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            itemView.setOnClickListener(this);
            name=itemView.findViewById(R.id.card_name_cart);
            price=itemView.findViewById(R.id.card_price_cart);
            eB=itemView.findViewById(R.id.amount_btn);
            productImage=itemView.findViewById(R.id.card_img_cart);




        }



        @Override
        public void onClick(View view) {
            Intent i=new Intent(context,DetailProductActivity.class);
            context.startActivity(i.putExtra("product",p));
        }
    }
}
