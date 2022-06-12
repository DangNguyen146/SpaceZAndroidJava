package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacezandroidjava.Model.CreateContactRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NFCReadActivity extends AppCompatActivity {
    int user2Id;
    TextView username;
    TextView tagName;
    TextView registration_date;
    ImageView imageView;
    Button btn;

    private NfcAdapter mNfcAdapter;

    LoadingDialalog ld;

    public NFCReadActivity(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcread);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        ld = new LoadingDialalog(this);
        ld.ShowDialog("Vui lòng đưa thẻ NFC vào", R.layout.nfc_dialog);
    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            ld.HideDialog();
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);
                readFromNFC(ndef);



        }
    }

    private void readFromNFC(Ndef ndef) {
        ld.HideDialog();
        if(ndef==null){
            username = (TextView) findViewById(R.id.tv_username);
            tagName = (TextView) findViewById(R.id.tv_tagname);
            registration_date = (TextView) findViewById(R.id.tv_registration_date);
            imageView = (ImageView) findViewById(R.id.imageView);
            user2Id=164;
            username.setText("demo");
            tagName.setText("demo");
            registration_date.setText("6/8/2022");
            final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int user1Id=pref.getInt("userId",-1);
            Call<String> message= ApiClient.getService().createContactRequest(new CreateContactRequest(user1Id,user2Id));

            message.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("failed", "onFailure: ");
                }
            });

            return;
        }
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String data = new String(ndefMessage.getRecords()[0].getPayload());
            data = data.replace("\u0002vi", "");
            String [] dataArr = data.split("\\|");
            username = (TextView) findViewById(R.id.tv_username);
            tagName = (TextView) findViewById(R.id.tv_tagname);
            registration_date = (TextView) findViewById(R.id.tv_registration_date);
            imageView = (ImageView) findViewById(R.id.imageView);
            user2Id=Integer.parseInt(dataArr[0]);
            username.setText(dataArr[1]);
            tagName.setText(dataArr[2]);
            registration_date.setText(dataArr[3]);

            ndef.close();
            final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int user1Id=pref.getInt("userId",-1);
              Call<String> message= ApiClient.getService().createContactRequest(new CreateContactRequest(user1Id,user2Id));

            message.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("failed", "onFailure: ");
                }
            });




        } catch (IOException | FormatException e) {
            e.printStackTrace();


        }
    }
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


}