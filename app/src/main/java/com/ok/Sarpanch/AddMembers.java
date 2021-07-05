package com.ok.Sarpanch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddMembers extends AppCompatActivity {

    private SharedPref sharedPref;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterAddMember mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    private Set<RecyclerNoteMember> member_list;
    List<RecyclerNoteMember> villagers;
    Button submitBtn;
    TextView msgToDisplay;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarpanch_add_members);
        init_progressDialog();
        initWebView();
        getSharedPref();
        member_list = new HashSet<>();
        msgToDisplay = findViewById(R.id.id_sarpanch_add_members_txt_main);
        //get the villager list from server and display it using recycler list view
        //with check box
        //submit button
        submitBtn = findViewById(R.id.id_sarpanch_add_member_submit_btn);

        submitBtn.setOnClickListener(v -> {
            if(member_list.size() > 0){
                progressDialog.show();
                int i=0;
                JSONObject jsonObject=new JSONObject();
                for(RecyclerNoteMember member : member_list)
                {
                    try {
                        jsonObject.put("villager_id_"+ i++, member.getUserID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                submit(jsonObject.toString());
            }
            else {
                Toast.makeText(AddMembers.this, "You have not choosen anyone!", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void getSharedPref(){
        sharedPref = new SharedPref(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        getVillagers(sharedPref.getVillageId());
    }
    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }


    void getVillagers(String vill_id) {

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

                            JSONArray userJson = obj.getJSONArray("villagersList");

                            villagers = new ArrayList<>();

                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject villager_ob = userJson.getJSONObject(i);

                                String user_id = villager_ob.getString("user_id");
                                String user_name = villager_ob.getString("user_name");
                                String user_email = villager_ob.getString("email");
                                String user_mobile = villager_ob.getString("mobile");

                                RecyclerNoteMember villager = new RecyclerNoteMember(user_name, user_id, user_email, user_mobile);
                                villagers.add(villager);
                            }
                            if(villagers.size() == 0){
                                msgToDisplay.setVisibility(View.VISIBLE);
                            }
                            else{
                                msgToDisplay.setVisibility(View.GONE);
                            }
                            buildRecyclerView(villagers);
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
                        params.put("village_id", vill_id);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_GET_VILLAGERS);
    }

    public void buildRecyclerView(List<RecyclerNoteMember> userNote) {
        mRecyclerView = findViewById(R.id.id_sarpanch_add_member_recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //update UI
        mAdapter = new RecyclerAdapterAddMember(userNote);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapterAddMember.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
            }

            @Override
            public void onCheckBoxSelect(int position) {
                member_list.add(villagers.get(position));
                Toast.makeText(AddMembers.this, ""+member_list.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCheckBoxDeselect(int position) {
                member_list.remove(villagers.get(position));
                Toast.makeText(AddMembers.this, ""+member_list.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void submit(String villager_ids){

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
                        Toast.makeText(getApplicationContext(), ""+obj.getString("message"), Toast.LENGTH_SHORT).show();
                        if (!obj.getBoolean("error")) {
                            finish();
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
                        params.put("vill_id",sharedPref.getVillageId());
                        params.put("villager_ids",villager_ids);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_VILLAGER_TO_MEMBER);
    }
}
