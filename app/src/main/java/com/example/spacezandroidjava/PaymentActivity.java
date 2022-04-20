package com.example.spacezandroidjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacezandroidjava.Config.Config;
import com.example.spacezandroidjava.databinding.ActivityMainBinding;
import com.example.spacezandroidjava.databinding.ActivityPaymentBinding;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
  private static final int PAYPAL_REQUEST_CODE=7171;
   private static PayPalConfiguration config=new PayPalConfiguration()
           .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
           .clientId(Config.PAYPAL_CLIENT_ID);
    TextView btnPay;
    EditText edAmount;
    int amount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        btnPay=(TextView) findViewById(R.id.btn_pay);
        edAmount=(EditText) findViewById(R.id.amountEditText);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,PayPalService.class));

    }

    private void processPayment() {
        Intent in=getIntent();
        amount=in.getIntExtra("total",1);
//        amount=edAmount.getText().toString();
        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(1),"USD","buy something",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent i=new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        i.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(i,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class).putExtra("PaymentDetails", paymentDetails).putExtra("PaymentAmount", amount));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else if(resultCode== Activity.RESULT_CANCELED) {
                Toast.makeText(this, "cancle", Toast.LENGTH_SHORT).show();
            }

        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "invalid", Toast.LENGTH_SHORT).show();
        }
    }
}