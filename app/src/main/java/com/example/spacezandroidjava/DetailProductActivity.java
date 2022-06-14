package com.example.spacezandroidjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.AddToCartRequest;
import com.example.spacezandroidjava.Model.AddToCartResponse;
import com.example.spacezandroidjava.Model.Cart;
import com.example.spacezandroidjava.Model.CommentRequest;
import com.example.spacezandroidjava.Model.GetCommnetsResponse;
import com.example.spacezandroidjava.Model.Product;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    private BottomSheetDialog  bottomSheetDialog;
    private List<Product> lstItemCart;
    private CommentAdapter commentAdapter;
    private RecyclerView rvComment;
    private Button sendCommentBtn;
    private EditText commentEd;
    private int productId;
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
//        TextView username_tv=(TextView) findViewById(R.id.user_lastName_firstName);
//        EditText  editText=new EditText(this);
        commentEd=(EditText) findViewById(R.id.comment_ed);
        ImageView imageView=(ImageView) findViewById(R.id.product_img);
        sendCommentBtn=(Button) findViewById(R.id.send_comment_icon);

        lstItemCart=new LinkedList<Product>();
        SharedPreferences recvPref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Gson gson=new Gson();
        String json=recvPref.getString("lstProduct","");
        Type listType = new TypeToken<LinkedList<Product>>(){}.getType();
        if(!json.equals(""))
            lstItemCart=gson.fromJson(json,listType);


        final SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String firstName=pref.getString("firstName","Your");
        String lastName=pref.getString("lastName","Name");
        int userId=pref.getInt("userId",-1);
        AlertDialog dialog=new AlertDialog.Builder(this).create();
        dialog.setTitle("Nhập nội dung");
//        dialog.setView(editText);
         productId=-1;
        if(intent.getExtras()!=null){
            Product p=(Product) intent.getSerializableExtra("product");
            String name= p.getName();
            productId=p.getId();
            Long price=p.getPrice();
            String imageUrl=p.getImage();
            int rate=p.getRate();
            rate_bar.setRating(rate);
            String description=p.getDescription();
            name_tv.setText(name);
            rvComment=(RecyclerView) findViewById(R.id.commnents);

            price_tv.setText("Giá: " +price.toString()+" VND");
            Picasso.get().load(imageUrl).into(imageView);
            description_tv.setText(description);
            btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    them vao gio hang db
                    addToCart(new AddToCartRequest(userId,p.getId()));
                    lstItemCart.add(p);
                    SharedPreferences savePref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor=savePref.edit();
                    Gson gson=new Gson();
                    String json=gson.toJson(lstItemCart);

                    editor.putString("lstProduct",json);
                    editor.commit();


                    bottomSheetDialog=new BottomSheetDialog(p);

                    bottomSheetDialog.show(getSupportFragmentManager(),"tag");


                }
            });



        }


        getAllCommentByProductId(Integer.toString(productId));

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=commentEd.getText().toString();

                sendComment(new CommentRequest(comment,productId,userId));

            }
        });

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                    username_tv.setText(editText.getText());
            }
        });

//        username_tv.setText(firstName+" "+lastName);
//        username_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editText.setText(username_tv.getText());
//                dialog.show();
//            }
//        });




    }
    public void addToCart(AddToCartRequest addToCartRequest){
        Call<AddToCartResponse> addToCartResponse=ApiClient.getService().addToCartResponse(addToCartRequest);
        addToCartResponse.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if(response.isSuccessful()){
                    Log.i("Call", "onResponse: success");
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                Log.i("Call", "onFailure: failed");
            }
        });


    }
    public void getAllCommentByProductId(String productId){
        LoadingDialalog ld=new LoadingDialalog(this);
        ld.ShowDialog("Đang tải");
        Call<List<GetCommnetsResponse>> getCommnetsResponseCall=ApiClient.getService().getCommnets(productId);
       getCommnetsResponseCall.enqueue(new Callback<List<GetCommnetsResponse>>() {
           @Override
           public void onResponse(Call<List<GetCommnetsResponse>> call, Response<List<GetCommnetsResponse>> response) {
               List<GetCommnetsResponse> comments=response.body();
                commentAdapter=new CommentAdapter(getApplicationContext(),comments);
               LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                rvComment.setAdapter(commentAdapter);
               rvComment.setLayoutManager(linearLayoutManager);
               ld.HideDialog();
           }

           @Override
           public void onFailure(Call<List<GetCommnetsResponse>> call, Throwable t) {

           }
       });

    }
    public void sendComment(CommentRequest commentRequest){
        Call<Object> objectCall=ApiClient.getService().sendComment(commentRequest);
        objectCall.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                Log.i("comment", "onResponse: ");

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}