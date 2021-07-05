package com.ok.Members.ManageServiceRequests;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Map;

public class ServiceTakers extends AppCompatActivity {

    private ArrayList<ServiceTakersItem> mServiceTakersList;
    private RecyclerView mRecyclerView;
    private ServiceTakersRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPref sharedPref;
    private ProgressDialog progressDialog;
    TextView msgToDisplay;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_service_takers);

        sharedPref = new SharedPref(this);
        initWebView();
        msgToDisplay = findViewById(R.id.id_member_serviceTakers_main_txt_main);
        init_progressDialog();

        //--------------------
        progressDialog.show();
        getServiceTakers(sharedPref.getVillageId());
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

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.id_member_serviceTakers_main_recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ServiceTakersRecyclerAdapter(mServiceTakersList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ServiceTakersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
            }

            @Override
            public void onRevokeClick(int position) {
                progressDialog.show();
                onRevoke(mServiceTakersList.get(position).getServiceID(),mServiceTakersList.get(position).getApplicantID() , position);
            }
        });
    }

    void onRevoke(String serviceID,String userID, int position){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    progressDialog.dismiss();
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (!obj.getBoolean("error")) {

                            mServiceTakersList.remove(position);
                            mAdapter.notifyItemRemoved(position);

                            if(mServiceTakersList.size() == 0){
                                msgToDisplay.setVisibility(View.VISIBLE);
                            }
                            else{
                                msgToDisplay.setVisibility(View.GONE);
                            }

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
                        params.put("service_id", serviceID);
                        params.put("user_id", userID);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_REVOKE_USER_SERVICE);
    }

    void getServiceTakers(String villageID){

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

                            JSONArray req_serviceTakers = obj.getJSONArray("serviceTakersList");

                            mServiceTakersList = new ArrayList<>();

                            for (int i = 0; i < req_serviceTakers.length(); i++) {
                                JSONObject serviceTaker_ob = req_serviceTakers.getJSONObject(i);
                                ServiceTakersItem serviceItem = new ServiceTakersItem(
                                        serviceTaker_ob.getString("service_name"),
                                        serviceTaker_ob.getString("service_id"),
                                        serviceTaker_ob.getString("applicant_name"),
                                        serviceTaker_ob.getString("applicant_id"),
                                        serviceTaker_ob.getString("mobile")
                                );
                                mServiceTakersList.add(serviceItem);
                            }
                            if(mServiceTakersList.size() == 0){
                                msgToDisplay.setVisibility(View.VISIBLE);
                            }
                            else{
                                msgToDisplay.setVisibility(View.GONE);
                            }
                            buildRecyclerView();

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

        webViewRequest.loadUrl(URLs.URL_GET_SERVICE_TAKERS);
    }
}
