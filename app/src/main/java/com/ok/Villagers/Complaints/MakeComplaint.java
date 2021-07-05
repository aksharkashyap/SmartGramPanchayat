package com.ok.Villagers.Complaints;

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
import com.ok.Home.VolleySingleton;
import com.ok.Sarpanch.Department.DepartmentItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeComplaint extends AppCompatActivity {

    private SharedPref sharedPref;
    private ProgressDialog progressDialog;
    private Button submit;
    private Spinner departmentSpinner;
    private EditText complaintMsg;
    ArrayAdapter spinner_adapter_department;
    TextView departmentHEAD;
    WebView webViewRequest;
    List<DepartmentItem> departmentItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villager_make_complaint);
        sharedPref = new SharedPref(this);
        init_view();
        initWebView();
        init_progressDialog();

        //-------------------
        setDepartmentSpinner(sharedPref.getVillageId());


        //------------------------------------
        submit.setOnClickListener(v -> {
            if(isValid()) {

                //-------generate complaint ID
                long leftLimit = 10000L,rightLimit = 12L;
                long generatedComplaintID = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
                String ComplaintID = String.valueOf(generatedComplaintID);

                progressDialog.show();
                makeComplaint(sharedPref.getUserId(), ComplaintID, departmentSpinner.getSelectedItemPosition());
            }
        });

        //-- on department selected listener
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(!departmentSpinner.getSelectedItem().toString().equals("Select Department")){
                    departmentHEAD.setText("Department Head: "+
                            departmentItems.get(departmentSpinner.getSelectedItemPosition()-1).getDepartmentHead());
                }
                else{
                    departmentHEAD.setText("Department Head: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


    void init_view(){
        departmentSpinner = findViewById(R.id.id_villager_make_complaint_departmentSpinner);
        complaintMsg = findViewById(R.id.id_villager_make_complaint_complaintMSG);
        departmentHEAD = findViewById(R.id.id_villager_make_complaint_departmentHead);
        submit = findViewById(R.id.id_villager_make_complaint_submit);
    }

    void init_progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    void makeComplaint(String userID, String complaintID, int idPos){

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
                        if (!obj.getBoolean("error")) { }

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();

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
                        params.put("complaint_id", complaintID);
                        params.put("complaint_msg", complaintMsg.getText().toString().trim());
                        params.put("department_id", departmentItems.get(idPos-1).getDepartmentId());
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_MAKE_COMPLAINT);
    }

    boolean isValid(){
        if(departmentSpinner.getSelectedItem().toString().equals("Select Department")){
            Toast.makeText(this, "Please Select Department!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(complaintMsg.getText().toString().trim().isEmpty()){
            complaintMsg.setError("Enter your complaint!");
            complaintMsg.requestFocus();
            return false;
        }

        return true;
    }

    void setDepartmentSpinner(String villageID){

        webViewRequest.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    //stop progressDialog
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            List<String> departmentNames = new ArrayList<>();
                            departmentItems = new ArrayList<>();
                            departmentNames.add("Select Department");
                            JSONArray departments_arr = obj.getJSONArray("departmentList");
                            //traversing through all the object
                            for (int i = 0; i < departments_arr.length(); i++) {

                                JSONObject dept_obj = departments_arr.getJSONObject(i);

                                DepartmentItem departmentItem = new DepartmentItem(
                                        dept_obj.getString("department_id"),
                                        dept_obj.getString("department_name"),
                                        dept_obj.getString("department_head"),
                                        dept_obj.getString("department_headID")
                                );

                                departmentNames.add(dept_obj.getString("department_name"));
                                departmentItems.add(departmentItem);
                            }
                            spinner_adapter_department = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, departmentNames);
                            spinner_adapter_department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            departmentSpinner.setAdapter(spinner_adapter_department);

                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error ->{
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

        webViewRequest.loadUrl(URLs.URL_GET_DEPARTMENT_INFO);
    }
}
