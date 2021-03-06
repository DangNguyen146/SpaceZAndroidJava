package com.example.spacezandroidjava;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.Contact;


import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    RecyclerView rv;
    LoadingDialalog loadingDialalog;
    private String mParam2;
    private  ContactAdapter contactAdapter;





    public MessFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessFragment newInstance(String param1, String param2) {
        MessFragment fragment = new MessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mess, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        int userId=pref.getInt("userId",-1);
        rv=(RecyclerView) getView().findViewById(R.id.rv_contact);

         loadingDialalog=new LoadingDialalog(getContext());
        loadingDialalog.ShowDialog("Ch??? x?? nh?? ");
        getContact(Integer.toString(userId));

        String userName=pref.getString("userName","unknow");


    }

    public void getContact(String id){
        Call<List<Contact>> contacts=ApiClient.getService().contactResponse(id);
        contacts.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {

                List<Contact> lstContact=response.body();
                loadingDialalog.HideDialog();
                contactAdapter=new ContactAdapter(lstContact, getActivity());
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                rv.setAdapter(contactAdapter);
                rv.setLayoutManager(linearLayoutManager);



            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }
        });

    }
}