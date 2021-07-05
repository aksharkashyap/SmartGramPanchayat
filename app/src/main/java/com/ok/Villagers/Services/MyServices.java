package com.ok.Villagers.Services;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.ok.Villagers.Complaints.RecyclerComplaintItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyServices extends AppCompatActivity {

    ListView mServicesListView;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    List<VillagerServiceItem> userServices;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villager_my_services);
        mServicesListView = findViewById(R.id.id_villager_list_view_services);
        init_progressDialog();
        initWebView();
        sharedPref = new SharedPref(this);
        //---------
        progressDialog.show();
        getUserServices(sharedPref.getUserId());
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

    void buildListView(){

        List<String> myServices = new ArrayList<>();
        for(int i=0;i<userServices.size();i++){
            myServices.add("Service ID: "+userServices.get(i).serviceID+ "\n"
                    +"Service Name: " +userServices.get(i).serviceName);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,myServices);
        mServicesListView.setAdapter(adapter);
    }

    void getUserServices(String userID){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    progressDialog.dismiss();

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            userServices = new ArrayList<>();
                            JSONArray userJson = obj.getJSONArray("userServices");

                            userServices = new ArrayList<>();

                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject service_ob = userJson.getJSONObject(i);

                                VillagerServiceItem villagerServiceItem = new VillagerServiceItem(
                                        service_ob.getString("service_id"),
                                        service_ob.getString("service_name")
                                );
                                userServices.add(villagerServiceItem);
                            }
                            if(userServices.size() == 0){
                                Toast.makeText(getApplicationContext(), "You are not availing any services!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else buildListView();

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
                        params.put("user_id", userID);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_USER_SERVICES_LIST);
    }
}
