package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spacezandroidjava.Model.UserInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tv_username;
    private ImageView iv_avatar;
    private LoadingDialalog loadingDialalog;
    private TextView tv_changeSetting;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context=getActivity();
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        int userId=pref.getInt("userId",-1);
        tv_username=(TextView) getView().findViewById(R.id.setting_username);
        tv_changeSetting=(TextView) getView().findViewById(R.id.setting_changeInfo);
        iv_avatar=(ImageView) getView().findViewById(R.id.setting_avatar);
        loadingDialalog =new LoadingDialalog(getContext());
        loadingDialalog.ShowDialog("Let's find who you are");
        getUserInfo(Integer.toString(userId));
        Log.i("stop", "onViewCreated: ");

    }
    public void getUserInfo(String id){

       Call<UserInformation> userInformationCall= ApiClient.getService().getUserInformationResponse(id);
       userInformationCall.enqueue(new Callback<UserInformation>() {
           @Override
           public void onResponse(Call<UserInformation> call, Response<UserInformation> response) {
              UserInformation userInformation= response.body();


              tv_username.setText(userInformation.getUserName());
               Glide.with(getActivity()).load(userInformation.getAvartar()).into(iv_avatar);
               loadingDialalog.HideDialog();
               tv_changeSetting.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent i=new Intent(getActivity(),ChangeUserInformationActivity.class);
                       startActivity(i.putExtra("userInfo",userInformation));
                   }
               });

           }

           @Override
           public void onFailure(Call<UserInformation> call, Throwable t) {
               Log.i("userInfo", "onFailure: ",t);

           }
       });

    }
}