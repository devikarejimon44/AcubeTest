package com.gipra.acubetest.Api;

import com.gipra.acubetest.Models.LoginModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("User/Authenticate")
    Call<LoginModel> Login(@Field("companyID") String companyid,
                           @Field("applicationID") String applicationid,
                           @Field("userName")String username,
                           @Field("password")String password);


//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
//    @POST("User/Authenticate")
//    Call<LoginModel> login(@Body LoginModel loginModel);



//
//        @Headers({
//                "Accept: application/json",
//                "Content-Type: application/json"
//        })
//        @POST("saveRawJSONData")
//        Call<JsonObject> postRawJSON(@Body JsonObject jsonObject);




}
