package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class NFCReadActivity extends AppCompatActivity {
    int id;
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
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String data = new String(ndefMessage.getRecords()[0].getPayload());
            data = data.replace("\u0002vi", "");
            String [] dataArr = data.split("\\|");
            username = (TextView) findViewById(R.id.w_username);
            tagName = (TextView) findViewById(R.id.w_tagname);
            registration_date = (TextView) findViewById(R.id.w_registration_date);
            imageView = (ImageView) findViewById(R.id.imageView);
            id=Integer.parseInt(dataArr[0]);
            username.setText(dataArr[1]);
            tagName.setText(dataArr[2]);
            registration_date.setText(dataArr[3]);

            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();
        }
    }

}