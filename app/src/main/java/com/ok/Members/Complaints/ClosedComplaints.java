package com.ok.Members.Complaints;

import android.app.ProgressDialog;
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

public class ClosedComplaints extends AppCompatActivity {

    private ArrayList<MemberComplaintItem> mClosedComplaintsList;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterClosedComplaints mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPref sharedPref;
    private ProgressDialog progressDialog;
    TextView msgToDisplay;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_closed_complaints);
        sharedPref = new SharedPref(this);
        initWebView();
        init_progressDialog();
        msgToDisplay = findViewById(R.id.id_member_closedComplaintMain_txt_main);
        //---------------
        progressDialog.show();
        getClosedComplaints(sharedPref.getUserId());
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
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.id_member_closedComplaintMain_recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapterClosedComplaints(mClosedComplaintsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapterClosedComplaints.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
            }
        });
    }

    private void getClosedComplaints(String headID){

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

                            JSONArray userJson = obj.getJSONArray("closedComplaintsList");

                            mClosedComplaintsList = new ArrayList<>();

                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject complaints_ob = userJson.getJSONObject(i);

                                MemberComplaintItem complaintItem = new MemberComplaintItem(
                                        complaints_ob.getString("complaint_id"),
                                        complaints_ob.getString("department"),
                                        null,
                                        complaints_ob.getString("complaint_msg"),
                                        complaints_ob.getString("complaint_status"),
                                        complaints_ob.getString("head_remarks"),
                                        complaints_ob.getString("complaint_date"),
                                        complaints_ob.getString("closing_date"),
                                        complaints_ob.getString("applicant_id"),
                                        complaints_ob.getString("applicant_name")
                                );

                                mClosedComplaintsList.add(complaintItem);
                            }
                            if(mClosedComplaintsList.size() == 0){
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
                        params.put("head_id", headID);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_CLOSED_COMPLAINTS_LIST_HEADWISE);
    }

}
