package com.ok.Home.MemberList;

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
import com.ok.Sarpanch.Adapters.RecyclerAdapterAddMember;
import com.ok.Sarpanch.AddMembers;
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MembersList extends AppCompatActivity{

    private SharedPref sharedPref;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterMember mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    TextView msgToDisplay;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        init_progressDialog();
        initWebView();
        getSharedPref();
        msgToDisplay = findViewById(R.id.id_home_membersList_layout_txt_main);
        getMembers();
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

    void getMembers(){
        progressDialog.show();
        fetchMembersFromServer(sharedPref.getVillageId());
    }

    private void getSharedPref(){
        sharedPref = new SharedPref(this);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }


    void fetchMembersFromServer(String villageId){

        webViewRequest.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    try {
                        progressDialog.dismiss();
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {

                            JSONArray userJson = obj.getJSONArray("memberList");

                            List<RecyclerNoteMember> memberList = new ArrayList<>();

                            for(int i = 0; i < userJson.length(); i++){
                                JSONObject member_ob = userJson.getJSONObject(i);

                                String member_id = member_ob.getString("member_id");
                                String member_name = member_ob.getString("member_name");
                                String member_email = member_ob.getString("member_email");
                                String member_mobile = member_ob.getString("member_mobile");

                                RecyclerNoteMember member = new RecyclerNoteMember(member_name, member_id, member_email, member_mobile);
                                memberList.add(member);
                            }
                            if(memberList.size() == 0){
                                msgToDisplay.setVisibility(View.VISIBLE);
                            }
                            else{
                                msgToDisplay.setVisibility(View.GONE);
                            }
                            buildRecyclerView(memberList);

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
                        params.put("village_id", villageId);
                        return params;
                    }

                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_GET_MEMBERS);
    }


    public void buildRecyclerView(List<RecyclerNoteMember> userNote) {
        mRecyclerView = findViewById(R.id.id_home_membersList_recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //update UI
        mAdapter = new RecyclerAdapterMember(userNote);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapterMember.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
            }
        });
    }
}