package com.example.spacezandroidjava;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.Charset;

public class NFCWriteActivity extends AppCompatActivity {

    TextView username;
    TextView tagName;
    TextView registration_date;
    Button btn;

    LoadingDialalog ld;

    private NfcAdapter mNfcAdapter;

    public NFCWriteActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcwrite);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        username = findViewById(R.id.w_username);
        tagName = findViewById(R.id.w_tagname);
        registration_date = findViewById(R.id.w_registration_date);

        ld = new LoadingDialalog(this);
        btn = findViewById(R.id.w_btn);
        btn.setOnClickListener(view -> ld.ShowDialog("Vui lòng đưa thẻ NFC vào", R.layout.nfc_dialog));
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected, tagDetected, ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_write_progress), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);
            WriteToNFC(ndef);
        }
    }
    private void WriteToNFC(Ndef ndef) {
        ld.HideDialog();
        if(ndef==null){
            Toast.makeText(this, getString(R.string.message_write_success), Toast.LENGTH_SHORT).show();
            return;
        }
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId=pref.getInt("userId",-1);

        try {
            String part0=Integer.toString(userId)+"|";
            String part1 = username.getText().toString() + "|";
            String part2  = tagName.getText().toString() + "|";
            String part3 = registration_date.getText().toString();
            String write_msg = part0 + part1 + part2 + part3 ;

            ndef.connect();
            NdefRecord mimeRecord = NdefRecord.createMime("text/plain", write_msg.getBytes(Charset.forName("UTF8")));
            ndef.writeNdefMessage(new NdefMessage(mimeRecord));
            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();
        }
    }
}