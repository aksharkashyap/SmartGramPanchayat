package com.ok.Villagers.Services;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ok.Home.R;
import com.ok.Home.SharedPref;
import com.ok.Home.URLs;
import com.ok.Home.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestService extends AppCompatActivity {

    Button requestServiceBtn;
    Spinner services_spinner;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    ArrayAdapter adapter;
    List<VillagerServiceItem> services_list;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villager_request_service);
        init_view();
        sharedPref = new SharedPref(this);
        init_progressDialog();
        initWebView();
        requestServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                requestService(services_list.get(services_spinner.getSelectedItemPosition()).serviceID, sharedPref.getUserId());
            }
        });
        //--------------------------
        progressDialog.show();
        setServicesSpinner(sharedPref.getVillageId());
        //show all services offered by the village in spinner
        //send selected service to server and add it service_request
        //service_request will be fetched by sarpanch
        //if sarpanch allows reuqest then add the service of user to service_takers

        //service_takers will be fetched by myservices by the user and sarpanch(as service_takers)
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

    void requestService(String serviceID, String userID){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    //stop progressDialog
                    progressDialog.dismiss();
                    try {
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {

                        }
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
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
                        params.put("service_id", serviceID);
                        params.put("user_id", userID);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_USER_REQUEST_SERVICE);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    void init_view(){
        requestServiceBtn = findViewById(R.id.id_villager_btn_requestService);
        services_spinner = findViewById(R.id.id_villager_sp_services);
    }


    void setServicesSpinner(String villageID){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    //stop progressDialog
                    progressDialog.dismiss();
                    try {
                        //converting response to json object
                        services_list = new ArrayList<>();
                        List<String> adapterArrayServices = new ArrayList<>();

                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            JSONArray services_arr = obj.getJSONArray("services");
                            for (int i = 0; i < services_arr.length(); i++) {
                                JSONObject service_obj = services_arr.getJSONObject(i);
                                VillagerServiceItem curr_service = new VillagerServiceItem(
                                        service_obj.getString("service_id"),
                                        service_obj.getString("service_name")
                                );
                                adapterArrayServices.add(service_obj.getString("service_name"));
                                services_list.add(curr_service);
                            }

                            if(adapterArrayServices.size() == 0){
                                Toast.makeText(getApplicationContext(), "Village is not providing any services, Come back later!", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, adapterArrayServices);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            services_spinner.setAdapter(adapter);

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
                        params.put("village_id", villageID);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_USER_GET_VILLAGE_SERVICES);
    }
}
