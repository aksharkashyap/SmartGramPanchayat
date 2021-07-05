package com.ok.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsUpdate extends AppCompatActivity {
    EditText NAME, MOBILE;
    EditText ADHAR, PAN, VOTER_ID;
    EditText DOB, BLOOD_GROUP;
    EditText FATHER,MOTHER;
    EditText MARITAL_STATUS;
    EditText MEMBER_COUNT;
    RadioGroup GENDER;
    RadioButton GENDER_MALE, GENDER_FEMALE;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_update);
        init_view();
        initWebView();
        init_progressDialog();
        sharedPref = new SharedPref(this);
        //--------------------
        progressDialog.show();
        getUserDetails(sharedPref.getUserId());
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

    void getUserDetails(String user_id){

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
                            User user = new User();
                            user.contactDetailsSetter(
                                    userJson.getString("mobile"),
                                    null,
                                    null,
                                    null,
                                    null
                            );
                            user.personalDetailsSetter(
                                    null,
                                    null,
                                    userJson.getString("name"),
                                    userJson.getString("gender"),
                                    userJson.getString("adhar"),
                                    userJson.getString("pan"),
                                    userJson.getString("voter_id"),
                                    userJson.getString("dob"),
                                    userJson.getString("blood_group"),
                                    userJson.getString("marital_status")
                            );
                            user.familyDetailsSetter(
                                    userJson.getString("father"),
                                    userJson.getString("mother"),
                                    userJson.getString("member_count")
                            );

                            set_text(user);

                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error ->{
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Server or Network Problem!", Toast.LENGTH_SHORT).show();
                })

                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("Cookie", cookies);
                        return map;
                    }
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id",user_id);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_USER_PROFILE);
    }

    public void onUpateClick(View view){
        if(isValid()){
            progressDialog.show();
            updateUserDetails(sharedPref.getUserId());
        }
    }

    public boolean isValid(){
        if(NAME.getText().toString().trim().isEmpty()){
            NAME.setError("Enter Name!");
            NAME.requestFocus();
            return false;
        }
        else if(NAME.getText().toString().trim().length() > 29){
            NAME.setError("Full Name length should be less than 30!");
            NAME.requestFocus();
            return false;
        }
        else if(MOBILE.getText().toString().trim().isEmpty()){
            MOBILE.setError("Enter Mobile!");
            MOBILE.requestFocus();
            return false;
        }
        else if(MOBILE.getText().toString().trim().length() != 10){
            MOBILE.setError("Invalid Mobile Number!");
            MOBILE.requestFocus();
            return false;
        }
        else if(ADHAR.getText().toString().trim().length() != 12){
            ADHAR.setError("Invalid Adhar Number!");
            ADHAR.requestFocus();
            return false;
        }
        else if(PAN.getText().toString().trim().isEmpty()){
            PAN.setError("Enter Pan Number!");
            PAN.requestFocus();
            return false;
        }
        else if(VOTER_ID.getText().toString().trim().isEmpty()){
            VOTER_ID.setError("Enter Voter ID!");
            VOTER_ID.requestFocus();
            return false;
        }
        else if(DOB.getText().toString().trim().isEmpty()){
            DOB.setError("Enter Date Of Birth!");
            DOB.requestFocus();
            return false;
        }
        else if(BLOOD_GROUP.getText().toString().trim().isEmpty()){
            BLOOD_GROUP.setError("Enter Blood Group!");
            BLOOD_GROUP.requestFocus();
            return false;
        }
        else if(FATHER.getText().toString().trim().isEmpty()){
            FATHER.setError("Enter Father Name!");
            FATHER.requestFocus();
            return false;
        }
        else if(MOTHER.getText().toString().trim().isEmpty()){
            MOTHER.setError("Enter Mother Name!");
            MOTHER.requestFocus();
            return false;
        }
        else if(MARITAL_STATUS.getText().toString().trim().isEmpty()){
            MARITAL_STATUS.setError("Enter Married/Unmarried!");
            MARITAL_STATUS.requestFocus();
            return false;
        }
        else if(MEMBER_COUNT.getText().toString().trim().isEmpty()){
            MEMBER_COUNT.setError("Enter Total Number of Family Members!");
            MEMBER_COUNT.requestFocus();
            return false;
        }
        else if(!GENDER_MALE.isChecked() && !GENDER_FEMALE.isChecked()){
            Toast.makeText(this, "Select Gender!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    void updateUserDetails(String user_id){

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
                        if (!obj.getBoolean("error")){
                            Toast.makeText(getApplicationContext(), "Profile Update Successful!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error ->{
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Server or Network Problem!", Toast.LENGTH_SHORT).show();
                })

                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("Cookie", cookies);
                        return map;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        String gender = GENDER_MALE.isChecked() ? "Male" : "Female";

                        Map<String, String> params = new HashMap<>();
                        params.put("user_id",user_id);
                        params.put("NAME",NAME.getText().toString());
                        params.put("MOBILE",MOBILE.getText().toString());
                        params.put("ADHAR",ADHAR.getText().toString());
                        params.put("PAN",PAN.getText().toString());
                        params.put("VOTER_ID",VOTER_ID.getText().toString());
                        params.put("DOB",DOB.getText().toString());
                        params.put("BLOOD_GROUP",BLOOD_GROUP.getText().toString());
                        params.put("FATHER",FATHER.getText().toString());
                        params.put("MOTHER",MOTHER.getText().toString());
                        params.put("MARITAL_STATUS",MARITAL_STATUS.getText().toString());
                        params.put("MEMBER_COUNT",MEMBER_COUNT.getText().toString());
                        params.put("GENDER",gender);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_USER_PROFILE_UPDATE);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }


    void init_view(){
        NAME = findViewById(R.id.id_user_details_update_et_name);
        MOBILE = findViewById(R.id.id_user_details_update_et_mobile);
        GENDER = findViewById(R.id.id_user_details_update_rg_gender);
        ADHAR = findViewById(R.id.id_user_details_update_et_adhar);
        PAN = findViewById(R.id.id_user_details_update_et_pan);
        VOTER_ID = findViewById(R.id.id_user_details_update_et_voter_id);
        DOB = findViewById(R.id.id_user_details_update_et_dob);
        BLOOD_GROUP = findViewById(R.id.id_user_details_update_et_blood_group);
        FATHER = findViewById(R.id.id_user_details_update_et_father);
        MOTHER = findViewById(R.id.id_user_details_update_et_mother);
        MARITAL_STATUS = findViewById(R.id.id_user_details_update_et_marital_status);
        MEMBER_COUNT = findViewById(R.id.id_user_details_update_et_member_count);
        GENDER_MALE = findViewById(R.id.id_user_details_update_rb_male);
        GENDER_FEMALE = findViewById(R.id.id_user_details_update_rb_female);
    }

    void set_text(User user){
        MOBILE.setText(user.getMOBILE());
        NAME.setText(user.getNAME());
        if(!user.getADHAR().equals("Information not provided")) ADHAR.setText(user.getADHAR());
        if(!user.getPAN().equals("Information not provided")) PAN.setText(user.getPAN());
        if(!user.getVOTER_ID().equals("Information not provided")) VOTER_ID.setText(user.getVOTER_ID());
        if(!user.getDOB().equals("Information not provided")) DOB.setText(user.getDOB());
        if(!user.getBLOOD_GROUP().equals("Information not provided")) BLOOD_GROUP.setText(user.getBLOOD_GROUP());
        if(!user.getFATHER().equals("Information not provided")) FATHER.setText(user.getFATHER());
        if(!user.getMOTHER().equals("Information not provided")) MOTHER.setText(user.getMOTHER());
        if(!user.getMARITAL_STATUS().equals("Information not provided")) MARITAL_STATUS.setText(user.getMARITAL_STATUS());
        if(!user.getMEMBER_COUNT().equals("Information not provided")) MEMBER_COUNT.setText(user.getMEMBER_COUNT());

        if(!user.getGENDER().equals("Information not provided")){
            if(user.getGENDER().equals("Male")) GENDER_MALE.setChecked(true);
            else GENDER_FEMALE.setChecked(true);
        }
    }
}
