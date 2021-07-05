package com.ok.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ok.Home.R;
import com.ok.Home.SharedPref;
import com.ok.Home.URLs;
import com.ok.Home.UserLoginInfo;
import com.ok.Home.VolleySingleton;
import com.ok.Members.MemberHome;
import com.ok.Sarpanch.SarpanchHome;
import com.ok.Villagers.VillagerHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLogin extends AppCompatActivity {

    EditText email, password;
    Button submit;
    ProgressDialog progressDialog;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signin);
        init_progressDialog();
        initWebView();
        email = findViewById(R.id.id_userLogin_et_email);
        password = findViewById(R.id.id_userLogin_et_password);
        submit = findViewById(R.id.id_userLogin_btn_submit);

        submit.setOnClickListener(v -> {
            if(isValid()){
                progressDialog.show();
                login();
            }
        });
    }

    public void callForgetPassword(View view){
        Intent intent = new Intent(this, UserForgetPassword.class);
        startActivity(intent);
    }

    public void callRegister(View view){
        Intent intent = new Intent(this, UserSignup.class);
        startActivity(intent);
    }

    void initWebView(){
        webViewRequest=new WebView(this);
        webViewRequest.getSettings().setJavaScriptEnabled(true);
        webViewRequest.setWebViewClient(new WebViewClient());
        webViewRequest.getSettings().setLoadWithOverviewMode(true);
        webViewRequest.getSettings().setUseWideViewPort(true);
        webViewRequest.getSettings().setSupportZoom(false);
        webViewRequest.getSettings().setBuiltInZoomControls(false);
        webViewRequest.getSettings().setDisplayZoomControls(false);
        webViewRequest.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewRequest.requestFocusFromTouch();
        webViewRequest.getSettings().setAppCacheEnabled(false);
        webViewRequest.setScrollbarFadingEnabled(false);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    boolean isValid(){
        if(email.getText().toString().trim().isEmpty()){
            email.requestFocus();
            email.setError("Enter Email!");
            return false;
        }
        else if(password.getText().toString().trim().isEmpty()){
            password.requestFocus();
            password.setError("Enter Password!");
            return false;
        }
        return true;
    }


    void login(){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    //stop progressDialog
                    progressDialog.dismiss();

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {

                            JSONObject userJson = obj.getJSONObject("user");

                            //creating a new user object
                            UserLoginInfo user = new UserLoginInfo(
                                    userJson.getString("user_id"),
                                    userJson.getString("email"),
                                    userJson.getInt("status"),
                                    userJson.getString("vill_id")
                            );

                            SharedPref sharedPref = new SharedPref(getApplicationContext());
                            sharedPref.write(user);

                            if(user.getSTATUS() == 0){
                                //Sarpanch
                                finish();
                                startActivity(new Intent(getApplicationContext(), SarpanchHome.class));
                            }
                            else if(user.getSTATUS() == 1){
                                //member
                                finish();
                                startActivity(new Intent(getApplicationContext(), MemberHome.class));
                            }
                            else{
                                //villager
                                finish();
                                startActivity(new Intent(getApplicationContext(), VillagerHome.class));
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        // e.printStackTrace();
                    }

                }, error ->{
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Server or Network Problem!", Toast.LENGTH_SHORT).show();
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email",email.getText().toString().trim());
                        params.put("password",password.getText().toString().trim());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("Cookie", cookies);
                        return map;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_LOGIN);
    }



    /*
    Redirect user to 3 different acitivities (1.Sarpanch 2.Villager 3.Member)
     based on email id(from server check)

    also use sharedPreferances to keep logged in

     */
}
