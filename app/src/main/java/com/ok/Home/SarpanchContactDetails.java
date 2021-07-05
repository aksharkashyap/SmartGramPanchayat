package com.ok.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ok.Home.R;
import com.ok.Home.SharedPref;
import com.ok.Home.URLs;
import com.ok.Home.User;
import com.ok.Home.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SarpanchContactDetails extends AppCompatActivity {

    TextView name,village,mobile,email;
    SharedPref sharedPref;
    ProgressDialog progressDialog;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarpanch_contact_details);
        init_view();
        initWebView();
        sharedPref = new SharedPref(this);
        init_progressDialog();
        //-------------
        progressDialog.show();
        getSarpanchContactInfo(sharedPref.getVillageId());
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
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

    void getSarpanchContactInfo(String village_id){

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

                            JSONObject userJson = obj.getJSONObject("sarpanchContactInfo");

                            if(userJson.length() > 0) {
                                //creating a new user object
                                String[] sarpanch_contact = new String[4];
                                sarpanch_contact[0] = userJson.getString("name");
                                sarpanch_contact[1] = userJson.getString("village");
                                sarpanch_contact[2] = userJson.getString("mobile");
                                sarpanch_contact[3] = userJson.getString("email");
                                set_text(sarpanch_contact);
                            }

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
                        params.put("village_id",village_id);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        webViewRequest.loadUrl(URLs.URL_GET_SARPANCH_CONTACT_INFO);
    }

    void init_view(){
        name = findViewById(R.id.id_sarpanch_contact_details_tv_name);
        village = findViewById(R.id.id_sarpanch_contact_details_tv_village);
        mobile = findViewById(R.id.id_sarpanch_contact_details_tv_mobile);
        email = findViewById(R.id.id_sarpanch_contact_details_tv_email);
    }

    void set_text(String[]user) {
        name.setText("Sarpanch Name: " + user[0]);
        village.setText("Village: " + user[1]);
        mobile.setText("Mobile: " + user[2]);
        email.setText("Email: " + user[3]);
    }
}
