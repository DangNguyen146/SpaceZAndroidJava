package com.example.spacezandroidjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import com.example.spacezandroidjava.Model.Cart;
import com.example.spacezandroidjava.Model.DeleteCartRequest;
import com.example.spacezandroidjava.Model.DeleteCartResponse;
import com.example.spacezandroidjava.Model.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    List<Product> listProduct;
    CartAdapter cartAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rv= (RecyclerView) findViewById(R.id.cart_lv);
        listProduct=new ArrayList<>();


        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int userId=pref.getInt("userId",-1);
        getCart(Integer.toString(userId) );
//        cartAdapter=new CartAdapter(listProduct);
//        lv.setAdapter(cartAdapter);
        itemTouchHelper.attachToRecyclerView(rv);
//

    }
    public void getCart(String id){
        Call<List<Cart>> myCart=ApiClient.getService().getMyCart(id);
        myCart.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                List<Cart> myProductCart=response.body();
//                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);


                rv.setAdapter(new CartAdapter(myProductCart,getApplicationContext()));
                rv.setLayoutManager(gridLayoutManager);

            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });

    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId=pref.getInt("userId",-1);
            int position= viewHolder.getLayoutPosition();
;

            DeleteCartRequest deleteCartRequest=new DeleteCartRequest(userId,Integer.parseInt(viewHolder.itemView.getTag().toString()));
            Call<DeleteCartResponse> deleteCartResponse=ApiClient.getService().deleteRequest(deleteCartRequest);
            deleteCartResponse.enqueue(new Callback<DeleteCartResponse>() {
                @Override
                public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {


                }

                @Override
                public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                    Log.i("asdsa", "onFailure: ");
                }
            });
//            cartAdapter.removeItem(position);
            cartAdapter.notifyItemRemoved(position);
            cartAdapter.notifyItemRangeChanged(position, cartAdapter.getItemCount()-1);

        }
//


    });
}