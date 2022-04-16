package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NFCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NFCFragment extends Fragment {

    private ImageView ReadNfcImg;
    private ImageView WriteNfcImg;

    private Context context;
    private NfcAdapter mNfcAdapter;

    public NFCFragment() {
    }

    public static NFCFragment newInstance() {
        return new NFCFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=this.getContext();
        ReadNfcImg = (ImageView) getView().findViewById(R.id.read_nfc_img);
        WriteNfcImg = (ImageView) getView().findViewById(R.id.write_nfc_img);


        ReadNfcImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),NFCReadActivity.class);
                startActivity(i);
            }
        });

        WriteNfcImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),NFCWriteActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}