package com.example.spacezandroidjava;

import com.example.spacezandroidjava.Model.AddToCartRequest;
import com.example.spacezandroidjava.Model.AddToCartResponse;
import com.example.spacezandroidjava.Model.AdjustAmount;
import com.example.spacezandroidjava.Model.Cart;

import com.example.spacezandroidjava.Model.ChangeUserInformationRequest;
import com.example.spacezandroidjava.Model.ChangeUserInformationRespone;
import com.example.spacezandroidjava.Model.CommentRequest;
import com.example.spacezandroidjava.Model.Contact;
import com.example.spacezandroidjava.Model.CreateContactRequest;
import com.example.spacezandroidjava.Model.DeleteCartRequest;
import com.example.spacezandroidjava.Model.DeleteCartResponse;
import com.example.spacezandroidjava.Model.GetCommnetsResponse;
import com.example.spacezandroidjava.Model.Product;
import com.example.spacezandroidjava.Model.UploadAvatarResponse;
import com.example.spacezandroidjava.Model.UserInformation;
import com.example.spacezandroidjava.Model.VerifyCodeRequest;
import com.example.spacezandroidjava.Model.VerifyCodeResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("user/login")
    Call<LoginRespon> loginRespon(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<RegisterRespon> registerRespon(@Body RegisterRequest registerRequest);

    @POST("cart/add-to-cart")
    Call<AddToCartResponse> addToCartResponse(@Body AddToCartRequest addToCartRequest);

    @GET("cart/{id}")
    Call<List<Cart>> getMyCart(@Path("id") String id);

    @HTTP(method = "DELETE", path = "cart/delete", hasBody = true)
    Call<DeleteCartResponse> deleteRequest(@Body DeleteCartRequest deleteCartRequest);

    @POST("cart/adjust")
    Call<Object> adjustAmount(@Body AdjustAmount adjustAmount);

    @GET("message/{id}")
    Call<List<Contact>> contactResponse(@Path("id") String id);

    @POST("message/create")
    Call<String> createContactRequest(@Body CreateContactRequest createContactRequest);

    @GET("user/{id}")
    Call<UserInformation> getUserInformationResponse(@Path("id") String id);

    @GET("user/isotp")
    Call<String> getChangeOtpResponse();

    @Multipart
    @POST("user/upload-avatar")

    Call<UploadAvatarResponse> uploadAvatar(@Header("token") String token,
            @Part MultipartBody.Part file);

    @POST("user/update")
    Call<ChangeUserInformationRespone> changeUserInformation(@Header("token") String token,
            @Body ChangeUserInformationRequest changeUserInformationRequest);

    @POST("user/verify")
    Call<VerifyCodeResponse> verifyCode(@Body VerifyCodeRequest verifyCodeRequest);

    @GET("product/comment/{id}")
    Call<List<GetCommnetsResponse>> getCommnets(@Path(("id")) String id);

    @POST("product/comment")
    Call<Object> sendComment(@Body CommentRequest request);

    @GET("product/find/{name}")
    Call<List<Product>> findProductByName(@Path(("name")) String name);
}
