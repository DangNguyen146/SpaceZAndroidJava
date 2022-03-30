package com.example.spacezandroidjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.Product;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.val;

public class DetailProductActivity extends AppCompatActivity {
    private BottomSheetDialog  bottomSheetDialog;
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
        TextView username_tv=(TextView) findViewById(R.id.user_lastName_firstName);
        EditText  editText=new EditText(this);



//        editText.setPadding(5,0,0,0);
//        Typeface type = Typeface.createFromAsset(getAssets(),"@font/baloo_tamma");
//        editText.setTypeface(type);
        AlertDialog dialog=new AlertDialog.Builder(this).create();
        dialog.setTitle("Nhập nội dung");
        dialog.setView(editText);
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
            btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog=new BottomSheetDialog(p);
                    bottomSheetDialog.show(getSupportFragmentManager(),"tag");

                }
            });


        }
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    username_tv.setText(editText.getText());
            }
        });
      final SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
      String firstName=pref.getString("firstName","Your");
      String lastName=pref.getString("lastName","Name");
        username_tv.setText(firstName+" "+lastName);
        username_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(username_tv.getText());
                dialog.show();
            }
        });




    }
}