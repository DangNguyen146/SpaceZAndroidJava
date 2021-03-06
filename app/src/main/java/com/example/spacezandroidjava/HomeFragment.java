package com.example.spacezandroidjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.spacezandroidjava.Model.Product;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView btn_cart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;
    private EditText edSearch;

    private Context context;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=this.getContext();

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = getView().findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderData(R.drawable.cover1));
        sliderDataArrayList.add(new SliderData(R.drawable.cover2));
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        btn_cart=(ImageView) getView().findViewById(R.id.cart_btn);
        gridView=  (GridView) getView().findViewById(R.id.gridView);
        LoadingDialalog loadingDialalog=new LoadingDialalog(getContext());
        loadingDialalog.ShowDialog("Ch??? x?? ??i, ??ang l???y h??ng v??? n?? :v");
        edSearch=(EditText) getView().findViewById(R.id.search_edittext);
        edSearch.setInputType(InputType.TYPE_NULL);
        edSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditTextClick();
            }
        });
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(),CartActivity.class);
                startActivity(i);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o=gridView.getItemAtPosition(i);
                Product product=(Product) o;
                Intent intent=new Intent(getActivity(),DetailProductActivity.class);
                startActivity(intent.putExtra("product",product));


            }
        });

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://spacezuit.herokuapp.com/api/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Product>> call=jsonPlaceHolderApi.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products=response.body();
                loadingDialalog.HideDialog();

                gridView.setAdapter(new ProductGridAdapter(context,products));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                loadingDialalog.HideDialog();
                loadingDialalog.ShowDialog("M???t m???ng r???i :((");
            }
        });

    }
    public void onEditTextClick()
    {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }



}