package com.example.spacezandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spacezandroidjava.Model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private  Product p;
    public BottomSheetDialog(Product p){
            this.p=p;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View view=inflater.inflate(R.layout.bottom_sheet_dialog,null,false);
        TextView productName_tv=(TextView) view.findViewById(R.id.product_name_sheet);
        TextView productPrice_tv=(TextView) view.findViewById(R.id.product_price_sheet);
        productPrice_tv.setText(p.getPrice().toString()+" vnd");
        productName_tv.setText(p.getName());
        TextView openCart=(TextView) view.findViewById(R.id.btn_add_to_cart_sheet);
        openCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CartActivity.class);
                startActivity(intent);
            }
        });

        return  view;

    }


}
