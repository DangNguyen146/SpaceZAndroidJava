package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.Product;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.val;

public class DetailProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Intent intent=getIntent();
        TextView name_tv=(TextView) findViewById(R.id.product_name);
    TextView price_tv=(TextView) findViewById(R.id.product_price);
        TextView description_tv=(TextView) findViewById(R.id.product_description);
        RatingBar rate_bar=(RatingBar) findViewById(R.id.rating_btn);
        Button btn_cart=(Button) findViewById(R.id.btn_add_to_cart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CharSequence[] items={"1","2","3"};
                MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(DetailProductActivity.this);
                builder.setTitle("Chọn nội dung hiển thị trên thẻ")
                        .setSingleChoiceItems(items, 0,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i("khfaskj", "onClick: ");
                            }
                        });


                builder.show();
            }
        });
        if(intent.getExtras()!=null){
            Product p=(Product) intent.getSerializableExtra("product");
            String name= p.getName();
            Long price=p.getPrice();
            int rate=p.getRate();
            rate_bar.setRating(rate);
            String description=p.getDescription();
            name_tv.setText(name);

            price_tv.setText("Giá: " +price.toString()+" VND");
            description_tv.setText(description);


        }


    }
}