package com.ok.Home;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.ok.Sarpanch.Adapters.RecyclerAdapterVillagerList;
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteVillagerList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VillagersList extends AppCompatActivity {

    SharedPref sharedPref;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterVillagerList mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    List<User> villagersProfileList;
    List<RecyclerNoteVillagerList> villagerRecyclerList;
    TextView msgToDisplay;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villagers_list);
        init_sharedPref();
        initWebView();
        init_progressDialog();
        progressDialog.show();
        msgToDisplay = findViewById(R.id.id_home_villagerList_layout_txt_main);
        getVillagersList(sharedPref.getVillageId());
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    void init_sharedPref(){
        sharedPref = new SharedPref(this);
    }


    public void buildRecyclerView(List<RecyclerNoteVillagerList> userNote) {
        mRecyclerView = findViewById(R.id.id_villagersList_recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //update UI
        mAdapter = new RecyclerAdapterVillagerList(userNote);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapterVillagerList.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // changeItem(position, "Clicked");
                Intent intent = new Intent(getApplicationContext(), DisplayUserProfile.class);
                intent.putExtra("user", villagersProfileList.get(position));
                startActivity(intent);
            }
        });
    }

    void getVillagersList(String vill_id) {

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

                            JSONArray userJson = obj.getJSONArray("villagersProfileList");

                            villagersProfileList = new ArrayList<>();
                            villagerRecyclerList = new ArrayList<>();

                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject villager_ob = userJson.getJSONObject(i);

                                String user_id = villager_ob.getString("user_id");
                                String user_name = villager_ob.getString("name");
                                String user_email = villager_ob.getString("email");
                                String user_mobile = villager_ob.getString("mobile");

                                String user_village = villager_ob.getString("village");
                                String user_city = villager_ob.getString("city");
                                String user_state = villager_ob.getString("state");
                                String user_gender = villager_ob.getString("gender");
                                String user_adhar = villager_ob.getString("adhar_number");
                                String user_pan = villager_ob.getString("pan_number");
                                String user_voter_id = villager_ob.getString("voter_id");
                                String user_dob = villager_ob.getString("date_of_birth");
                                String user_blood_group = villager_ob.getString("blood_group");
                                String user_father = villager_ob.getString("father");
                                String user_mother = villager_ob.getString("mother");
                                String user_marital_status = villager_ob.getString("merital_status");
                                String user_totalMembers = villager_ob.getString("member_count");

                                //-------you can use this user to display complete profile----
                                User user = new User();
                                user.contactDetailsSetter(user_mobile,user_email,user_village,user_city,user_state);
                                user.personalDetailsSetter(user_id,vill_id,user_name,user_gender,user_adhar,
                                        user_pan,user_voter_id,user_dob,user_blood_group,user_marital_status);
                                user.familyDetailsSetter(user_father,user_mother,user_totalMembers);
                                //-----------
                                villagersProfileList.add(user);

                                RecyclerNoteVillagerList villager = new RecyclerNoteVillagerList(user_name, user_id, user_email, user_mobile);
                                villagerRecyclerList.add(villager);
                            }
                            if(villagerRecyclerList.size() == 0){
                                msgToDisplay.setVisibility(View.VISIBLE);
                            }
                            else{
                                msgToDisplay.setVisibility(View.GONE);
                            }
                            buildRecyclerView(villagerRecyclerList);
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

        webViewRequest.loadUrl(URLs.URL_GET_VILLAGER_PROFILE_LIST);
    }

}
