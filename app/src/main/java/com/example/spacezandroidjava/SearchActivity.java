package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
private EditText searchEd;
private ProductGridAdapter productGridAdapter;
private GridView findGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEd=(EditText) findViewById(R.id.search_activity_edittext) ;
    findGridView=(GridView) findViewById(R.id.gridView_find);

       searchEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {


           @Override
           public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
               if (i == EditorInfo.IME_ACTION_SEARCH ||
                       i == EditorInfo.IME_ACTION_DONE ||
                       keyEvent != null &&
                               keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                               keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                   if (keyEvent == null || !keyEvent.isShiftPressed()) {
                       // the user is done typing.
                       String name=searchEd.getText().toString();
                      Call<List< Product>> productCall=ApiClient.getService().findProductByName(name);
                      productCall.enqueue(new Callback<List<Product>>() {
                          @Override
                          public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {


                              List<Product> products=response.body();

                              findGridView.setAdapter(new ProductGridAdapter(getApplicationContext(),products));


                          }

                          @Override
                          public void onFailure(Call<List<Product>> call, Throwable t) {

                          }
                      });
                       return true; // consume.
                   }
               }
               return false;
           }
       });
       findGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Object o=findGridView.getItemAtPosition(i);
               Product product=(Product) o;
               Intent intent=new Intent(getApplicationContext(),DetailProductActivity.class);
               startActivity(intent.putExtra("product",product));

           }
       });

    }
}