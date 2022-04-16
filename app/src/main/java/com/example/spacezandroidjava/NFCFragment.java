package com.example.spacezandroidjava;

import android.content.Intent;
import android.media.Image;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NFCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NFCFragment extends Fragment {

    ImageView ReadNfcImg;
    ImageView WriteNfcImg;

    private NfcAdapter mNfcAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NFCFragment() {
    }

    public static NFCFragment newInstance(String param1, String param2) {
        NFCFragment fragment = new NFCFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_n_f_c, container, false);
    }
}