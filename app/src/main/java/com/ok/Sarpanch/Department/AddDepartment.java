package com.ok.Sarpanch.Department;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.ok.Home.User;
import com.ok.Home.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDepartment extends AppCompatActivity {

    EditText department_name_et;
    Button submitBtn;
    Spinner department_head_spinner;
    SharedPref sharedPref;
    ArrayAdapter deparmentHeadAdapter;
    ProgressDialog progressDialog;
    List<String> memberIDs;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_add);
        department_name_et = findViewById(R.id.id_department_et_dept_name);
        submitBtn = findViewById(R.id.id_department_btn_submit);
        department_head_spinner = findViewById(R.id.id_department_spinner_department_head);
        sharedPref = new SharedPref(this);
        initWebView();
        init_adapter_spinner();
        init_progressDialog();
        setDepartmentHeadSpinner();
        submitBtn.setOnClickListener(v -> {

            if(isValid()) {
                long leftLimit = 1000L,rightLimit = 12L;
                long generatedDepartmentID = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
                String departmentID = String.valueOf(generatedDepartmentID);
                progressDialog.show();
                submit(departmentID, sharedPref.getVillageId());
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

    void init_adapter_spinner(){
        deparmentHeadAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Arrays.asList("Select Department Head"));
        deparmentHeadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_head_spinner.setAdapter(deparmentHeadAdapter);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    void submit(String departmentID, String village_id){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    progressDialog.dismiss();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
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

                        StringBuilder deptName = new StringBuilder(department_name_et.getText().toString().trim());
                        deptName.setCharAt(0,Character.toUpperCase(deptName.charAt(0)));

                        Map<String, String> params = new HashMap<>();
                        params.put("department_id", departmentID);
                        params.put("department_name", deptName.toString());
                        params.put("department_headID", memberIDs.get(department_head_spinner.getSelectedItemPosition()-1));
                        params.put("village_id", village_id);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_ADD_DEPARTMENT);
    }


    boolean isValid(){
        if(department_name_et.getText().toString().trim().isEmpty()){
            department_name_et.requestFocus();
            department_name_et.setError("Enter Department Name!");
            return false;
        }
        else if(department_head_spinner.getSelectedItem().toString().equals("Select Department Head")){
            Toast.makeText(this, "Please Select department head (Note- atleast 1 member must be there " +
                    "in village", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    void setDepartmentHeadSpinner(){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    try {
                        //converting response to json object
                        List<String> villageMembers = new ArrayList<>();
                        villageMembers.add("Select Department Head");
                        memberIDs = new ArrayList<>();
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            JSONArray villageMembers_arr = obj.getJSONArray("memberList");
                            //traversing through all the object
                            for (int i = 0; i < villageMembers_arr.length(); i++) {
                                JSONObject villagerMembers_obj = villageMembers_arr.getJSONObject(i);
                                String curr_villageMember = villagerMembers_obj.getString("member_name");
                                String curr_villageMemberID = villagerMembers_obj.getString("member_id");
                                villageMembers.add(curr_villageMember);
                                memberIDs.add(curr_villageMemberID);
                            }
                            deparmentHeadAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, villageMembers);
                            deparmentHeadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            department_head_spinner.setAdapter(deparmentHeadAdapter);
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
                        params.put("village_id",sharedPref.getVillageId());
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_GET_MEMBERS);
    }
}
