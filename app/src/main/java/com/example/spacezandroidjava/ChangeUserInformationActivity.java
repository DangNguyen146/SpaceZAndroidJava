package com.example.spacezandroidjava;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spacezandroidjava.Model.ChangeUserInformationRequest;
import com.example.spacezandroidjava.Model.ChangeUserInformationRespone;
import com.example.spacezandroidjava.Model.RealPathUtil;
import com.example.spacezandroidjava.Model.UploadAvatarResponse;
import com.example.spacezandroidjava.Model.UserInformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeUserInformationActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageView iv_changeUserInfo;
    private Uri mUri;
private String firstName;
private  String lastName;
private String email;
private  String userName;
private EditText et_changeUserInfoName;
    private EditText et_changeUserInfoFirstName;
    private EditText getEt_changeUserInfoLastName;
    private  EditText getEt_changeUserInfoEmail;
    private Button changeInfoBtn;

private final int GALLERY_REQ_CODE=1000;
private ActivityResultLauncher<Intent> mActivityResultLauncher=registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                    if(data==null){
                        return;
                    }
                    Uri uri=data.getData();
                    mUri =uri;
                    try {
                        Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);


                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

            }
        }
}
);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_information);
        Intent intent=getIntent();
        iv_changeUserInfo=findViewById(R.id.iv_changeUserInfo);
        et_changeUserInfoName=findViewById(R.id.et_changeUserInfoName);
        et_changeUserInfoFirstName=findViewById(R.id.et_changeFirstname_change);
        getEt_changeUserInfoLastName=findViewById(R.id.et_changeLastname_change);
        getEt_changeUserInfoEmail=findViewById(R.id.et_email_change);
        changeInfoBtn=(Button) findViewById(R.id.setting_save);
        if(intent.getExtras()!=null){
            UserInformation info= (UserInformation) intent.getSerializableExtra("userInfo");
            String avartar=info.getAvartar();
             firstName=info.getFirstName();
             lastName=info.getLastName();
//            iv_changeUserInfo.setImageURI(Uri.parse(avartar));

            Glide.with(this).load(avartar).circleCrop().into(iv_changeUserInfo);


             this.userName=info.getUserName();
            email=info.getEmail();
            et_changeUserInfoName.setText(userName);
            et_changeUserInfoFirstName.setText(firstName);
            getEt_changeUserInfoLastName.setText(lastName);
            getEt_changeUserInfoEmail.setText(email);
        }
        iv_changeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
//                Intent iGallery=new Intent(Intent.ACTION_PICK);
//                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(iGallery,GALLERY_REQ_CODE);


            }
        });
        changeInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserInformation();
                finish();
            }
        });

    }
    private void onClickRequestPermission(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        }
        else{
            String [] permission={Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }

    }

    private void openGallery(){
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResultLauncher.lauch(Intent.createChooser(intent,"Select picture"));
        startActivityForResult(Intent.createChooser(intent,"Chọn 1 tấm ảnh"),1);
    }
    private void uploadAvatar(Uri imageUri){
//        LoadingDialalog dialalog=new LoadingDialalog(getBaseContext());
//        dialalog.ShowDialog("Đang cập nhật");


       String strRealPath= RealPathUtil.getRealPath(this,imageUri);
        Log.e("uploadAvatar: ",strRealPath );
        File file=new File(strRealPath);
        RequestBody requestBodyAvt=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part multipartBodyAvt=MultipartBody.Part.createFormData("avatar",file.getName(),requestBodyAvt);
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token=pref.getString("token","");
        ApiClient.getService().uploadAvatar(token,multipartBodyAvt).enqueue(new Callback<UploadAvatarResponse>() {
            @Override
            public void onResponse(Call<UploadAvatarResponse> call, Response<UploadAvatarResponse> response) {
                UploadAvatarResponse data=response.body();
//                dialalog.HideDialog();
            }

            @Override
            public void onFailure(Call<UploadAvatarResponse> call, Throwable t) {
                Toast.makeText(ChangeUserInformationActivity.this,"Call api failed",Toast.LENGTH_SHORT).show();
                Log.i("upload", "onFailure: ",t.getCause());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(data!=null){
//        iv_changeUserInfo.setImageURI(data.getData());
        Glide.with(this).load(data.getData()).circleCrop().into(iv_changeUserInfo);
        uploadAvatar(data.getData());


    }
    }
    public void changeUserInformation(){
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token=pref.getString("token","");
        ChangeUserInformationRequest changeUserInformationRequest=new ChangeUserInformationRequest(getEt_changeUserInfoEmail.getText().toString(),et_changeUserInfoName.getText().toString(),et_changeUserInfoFirstName.getText().toString(),getEt_changeUserInfoLastName.getText().toString());
        ApiClient.getService().changeUserInformation(token,changeUserInformationRequest).enqueue(new Callback<ChangeUserInformationRespone>() {
            @Override
            public void onResponse(Call<ChangeUserInformationRespone> call, Response<ChangeUserInformationRespone> response) {
                Log.i("ChangeInfo", "onResponse: ");

            }

            @Override
            public void onFailure(Call<ChangeUserInformationRespone> call, Throwable t) {
                Log.i("Failed", "onFailure: ",t.getCause());
            }
        });
    }
}