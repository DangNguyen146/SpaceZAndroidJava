package com.example.spacezandroidjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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
    TextView tv_total;
    LoadingDialalog loadingDialalog;
    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rv= (RecyclerView) findViewById(R.id.cart_lv);
        listProduct=new ArrayList<>();


        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int userId=pref.getInt("userId",-1);
         loadingDialalog=new LoadingDialalog(this);
        loadingDialalog.ShowDialog("Check kỹ giỏ hàng nhaaaaaaa");
        getCart(Integer.toString(userId) );

        itemTouchHelper.attachToRecyclerView(rv);
            btnPay=(Button) findViewById(R.id.pay_btn);
            Intent i=new Intent(this,PaymentActivity.class);
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(i);
                }
            });


    }


    public void getCart(String id){
        Call<List<Cart>> myCart=ApiClient.getService().getMyCart(id);
        myCart.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                List<Cart> myProductCart=response.body();
                loadingDialalog.HideDialog();

                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
                tv_total=(TextView) findViewById(R.id.total_number);
                cartAdapter=new CartAdapter(myProductCart,getApplicationContext(),tv_total);
                rv.setAdapter(cartAdapter);
                rv.setLayoutManager(gridLayoutManager);
                int total= cartAdapter.calculateTotal();

                tv_total.setText(Integer.toString(total)+" đ");


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


        }



    });
}