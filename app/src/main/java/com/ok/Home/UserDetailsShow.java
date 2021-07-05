package com.ok.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
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

public class UserDetailsShow extends AppCompatActivity {

    private TextView UID, VILL_ID;
    private TextView MOBILE, EMAIL;
    private TextView VILLAGE, CITY, STATE;
    private TextView NAME, GENDER;
    private TextView ADHAR, PAN, VOTER_ID;
    private TextView DOB, BLOOD_GROUP;
    private TextView FATHER,MOTHER;
    private TextView MARITAL_STATUS;
    private TextView MEMBER_COUNT;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);
        sharedPref = new SharedPref(this);
        init_progressDialog();
        initWebView();
        init_view();
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

    public void callUpdateDetails(View view){
        Intent intent = new Intent(this,UserDetailsUpdate.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        getDetails(sharedPref.getUserId());
    }

    void getDetails(String user_id){

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
                                    userJson.getString("email"),
                                    userJson.getString("village"),
                                    userJson.getString("city"),
                                    userJson.getString("state")
                            );
                            user.personalDetailsSetter(
                                    userJson.getString("user_id"),
                                    userJson.getString("vill_id"),
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

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    void init_view(){
        UID = findViewById(R.id.id_display_user_profile_UID);
        VILL_ID = findViewById(R.id.id_display_user_profile_VILL_ID);
        MOBILE = findViewById(R.id.id_display_user_profile_MOBILE);
        EMAIL = findViewById(R.id.id_display_user_profile_EMAIL);
        VILLAGE = findViewById(R.id.id_display_user_profile_VILLAGE);
        CITY = findViewById(R.id.id_display_user_profile_CITY);
        STATE = findViewById(R.id.id_display_user_profile_STATE);
        NAME = findViewById(R.id.id_display_user_profile_NAME);
        GENDER = findViewById(R.id.id_display_user_profile_GENDER);
        ADHAR = findViewById(R.id.id_display_user_profile_ADHAR);
        PAN = findViewById(R.id.id_display_user_profile_PAN);
        VOTER_ID = findViewById(R.id.id_display_user_profile_VOTER_ID);
        DOB = findViewById(R.id.id_display_user_profile_DOB);
        BLOOD_GROUP = findViewById(R.id.id_display_user_profile_BLOOD_GROUP);
        FATHER = findViewById(R.id.id_display_user_profile_FATHER);
        MOTHER = findViewById(R.id.id_display_user_profile_MOTHER);
        MARITAL_STATUS = findViewById(R.id.id_display_user_profile_MERITAL_STATUS);
        MEMBER_COUNT = findViewById(R.id.id_display_user_profile_MEMBER_COUNT);
    }

    void set_text(User user){
        UID.setText("User ID: " + user.getUID());
        VILL_ID.setText("Village ID: " +user.getVILL_ID());
        MOBILE.setText("Mobile: " +user.getMOBILE());
        EMAIL.setText("Email: " +user.getEMAIL());
        VILLAGE.setText("Village: " +user.getVILLAGE());
        CITY.setText("City: " +user.getCITY());
        STATE.setText("State: " +user.getSTATE());
        NAME.setText("Name: " +user.getNAME());
        GENDER.setText("Gender: " +user.getGENDER());
        ADHAR.setText("Adhar: " +user.getADHAR());
        PAN.setText("Pan: " +user.getPAN());
        VOTER_ID.setText("Voter ID: " +user.getVOTER_ID());
        DOB.setText("DOB: " +user.getDOB());
        BLOOD_GROUP.setText("Blood Group: " +user.getBLOOD_GROUP());
        FATHER.setText("Father: " +user.getFATHER());
        MOTHER.setText("Mother: " +user.getMOTHER());
        MARITAL_STATUS.setText("Marital Status: " +user.getMARITAL_STATUS());
        MEMBER_COUNT.setText("Total Members: " +user.getMEMBER_COUNT());
    }
}
