package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.spacezandroidjava.Model.AdjustAmount;
import com.example.spacezandroidjava.Model.Cart;
import com.example.spacezandroidjava.Model.Contact;
import com.example.spacezandroidjava.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdapter extends   RecyclerView.Adapter<ContactAdapter.ViewHolder>  {
    private List<Contact> lstContact;
    private Context context;
    public ContactAdapter(List<Contact> lstContact,Context context){
        this.lstContact=lstContact;
        this.context=context;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.message_card,parent,false);
        ContactAdapter.ViewHolder viewHolder=new ContactAdapter.ViewHolder(itemView);
        return  viewHolder;




    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        Contact contact=lstContact.get(position);


        holder.name.setText(product.getName());

        holder.price.setText("Giá:"+ product.getPrice().toString());
        holder.itemView.setTag(product.getId());
        holder.eB.setNumber(Integer.toString(cart.getAmount()) );



    }
    @Override
    public int getItemCount() {
        return lstContact==null?0: lstContact.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView price;
        Context context;

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



        }



        @Override
        public void onClick(View view) {
            Intent i=new Intent(context,DetailProductActivity.class);
            context.startActivity(i.putExtra("product",p));
        }
    }

}
