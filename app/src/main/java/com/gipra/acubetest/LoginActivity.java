package com.gipra.acubetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.gipra.acubetest.Api.ApiClient;
import com.gipra.acubetest.Api.ApiInterface;
import com.gipra.acubetest.Api.ServiceGenerator;
import com.gipra.acubetest.Models.LoginModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText companyid,applicationid,username,password;
    Button btnLogin;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        companyid=findViewById(R.id.companyid);
        applicationid=findViewById(R.id.applicationid);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login();
            }
        });

    }
    private void Login() {

        String cid=companyid.getText().toString();
        String aid=applicationid.getText().toString();
        String uname=username.getText().toString();
        String psd=password.getText().toString();

        ApiInterface api=ApiClient.getClient().create(ApiInterface.class);
        Call<LoginModel>usercall=api.Login(cid,aid,uname,psd);
        usercall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body().getMessage().equals("Success")){
                    Log.i("onResponse", new Gson().toJson(response.body()));
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "You are succesfully logged in", Toast.LENGTH_SHORT).show();
                    String username=response.body().getUserName();
                    String rollname= String.valueOf(response.body().getRoleName());
                    String facilityid= String.valueOf(response.body().getFacilityId());
                    String tocken =response.body().getToken();
                    Log.i(TAG, "tocken : " + tocken);

                    SharedPreferences sharedPreferences;
                    sharedPreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("USERNAME",username);
                    editor.putString("ROLLNAME",rollname);
                    editor.putString("FACILITYID",facilityid);
                    editor.putString("TOKEN",tocken);
                    editor.commit();
                }
                else {
                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went error,Please try again later", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });



//
//        // Using the Retrofit
//        ApiInterface jsonPostService = ServiceGenerator.createService(ApiInterface.class, "http://acuberfid.fortiddns.com:4480/lmsapi/lms/");
//        Call<JsonObject> call = jsonPostService.postRawJSON(jsonObject);
//        call.enqueue(new Callback<JsonObject>() {
//
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try{
//                 // Log.e("response-success", response.body().toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//               // Log.e("response-failure", call.toString());
//            }
//        });



    }
}

















