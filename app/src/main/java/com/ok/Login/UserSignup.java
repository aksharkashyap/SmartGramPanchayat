package com.ok.Login;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ok.Home.MainActivity;
import com.ok.Home.R;
import com.ok.Home.SharedPref;
import com.ok.Home.URLs;
import com.ok.Home.User;
import com.ok.Home.UserLoginInfo;
import com.ok.Home.VolleySingleton;
import com.ok.Members.MemberHome;
import com.ok.Sarpanch.SarpanchHome;
import com.ok.Villagers.VillagerHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSignup extends AppCompatActivity {
    EditText fullName_et, email_et, mobileNum;
    EditText password_et, confirmPassword_et;
    Spinner state_sp, city_sp, village_sp;
    Spinner security_question_sp;
    EditText securityAnswer_et;
    Switch sarpanch_sw;
    ProgressDialog progressDialog;
    Button register;
    String[] security_questions;
    ArrayAdapter state_adapter, city_adapter, village_adapter;
    String VILLID;
    WebView webViewRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        initView();
        init_progressDialog();
        setSecurityQuestionSpinner();
        //------------------
        initWebView();
        progressDialog.show();
        setStateSpinner();

        //-------------
        register.setOnClickListener(v -> onSubmit());
    }

    public void callSignIn(View view){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
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

    void setStateSpinner(){

        webViewRequest.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    progressDialog.dismiss();
                    //stop progressDialog
                    try {
                        //converting response to json object
                        List<String> states_list = new ArrayList<>();
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            JSONArray states_arr = obj.getJSONArray("states");
                            //traversing through all the object
                            for (int i = 0; i < states_arr.length(); i++) {
                                //getting state_obj object from json array
                                JSONObject state_obj = states_arr.getJSONObject(i);
                                //adding the state to states_list
                                String curr_state = state_obj.getString("state");
                                states_list.add(curr_state);
                            }
                            state_adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states_list);
                            state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            state_sp.setAdapter(state_adapter);

                            state_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    /*--- to avoid mixing the states,villages and city of different recordes(user submits the data
                                      and refreshing is still going on so old city,village with new state will get inserted)
                                      until the state data loads just reset
                                      the city and village to "Select" and make a validation while submitting that if "Select" is there
                                      then dont submit the data
                                    */
                                    city_sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Select"))));

                                    village_sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Select"))));
                                    //----
                                    String selectedState = state_sp.getSelectedItem().toString();
                                    initWebView();
                                    setCitySpinner(selectedState);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

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
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        webViewRequest.loadUrl(URLs.URL_GET_STATES);
    }

    void setCitySpinner(String state_arg){

        webViewRequest.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

                    //stop progressDialog
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                        List<String> cities_list = new ArrayList<>();
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            JSONArray cities_arr = obj.getJSONArray("cities");
                            for (int i = 0; i < cities_arr.length(); i++) {
                                JSONObject city_obj = cities_arr.getJSONObject(i);
                                String curr_city = city_obj.getString("city");
                                cities_list.add(curr_city);
                            }
                            city_adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, cities_list);
                            city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            city_sp.setAdapter(city_adapter);

                            city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                                    village_sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Select"))));

                                    String selectedCity = city_sp.getSelectedItem().toString();
                                    initWebView();
                                    setVillageSpinner(selectedCity, state_arg);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

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
                        params.put("state_arg", state_arg);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        webViewRequest.loadUrl(URLs.URL_GET_CITIES);
    }


    void setVillageSpinner(String city_arg, String state_arg){

        webViewRequest.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);//here you will get cookie

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {


                    //stop progressDialog
                    try {
                        //converting response to json object
                        List<String> villages_list = new ArrayList<>();
                        Map<String, String> villMap = new HashMap<>();
                        JSONObject obj = new JSONObject(response);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            JSONArray villages_arr = obj.getJSONArray("villages");
                            for (int i = 0; i < villages_arr.length(); i++) {
                                JSONObject village_obj = villages_arr.getJSONObject(i);
                                String curr_village = village_obj.getString("village");
                                String curr_village_id = village_obj.getString("vill_id");
                                villages_list.add(curr_village);
                                villMap.put(curr_village, curr_village_id);
                            }
                            village_adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, villages_list);
                            village_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            village_sp.setAdapter(village_adapter);

                            village_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    String vill = village_sp.getSelectedItem().toString();
                                    VILLID = villMap.get(vill);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });


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
                        params.put("city_arg", city_arg);
                        params.put("state_arg", state_arg);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        webViewRequest.loadUrl(URLs.URL_GET_VILLAGES);
    }

    void setSecurityQuestionSpinner() {
        security_questions = new String[]{"Your First Pet Name", "Birth Place", "Your First School", "You wanna put some random words"};
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, security_questions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        security_question_sp.setAdapter(aa);
    }

    void onSubmit() {
        long leftLimit = 1000L,rightLimit = 12L;
        long generatedUID = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        String UID = String.valueOf(generatedUID);

        boolean isSarpanch = sarpanch_sw.isChecked();

        StringBuilder fullName = new StringBuilder(fullName_et.getText().toString());
        if(fullName.length() > 0) fullName.setCharAt(0,Character.toUpperCase(fullName.charAt(0)));

        UserLoginInfo loginInfo = new UserLoginInfo(
                UID,
                fullName.toString().trim(),
                email_et.getText().toString().trim(),
                password_et.getText().toString().trim(),
                confirmPassword_et.getText().toString(),
                security_question_sp.getSelectedItem().toString().trim(),
                securityAnswer_et.getText().toString().trim(),
                isSarpanch
        );

        User user = new User();
        user.contactDetailsSetter(
                mobileNum.getText().toString().trim(),
                email_et.getText().toString().trim(),
                village_sp.getSelectedItem().toString(),
                city_sp.getSelectedItem().toString(),
                state_sp.getSelectedItem().toString()
        );


        boolean isValid = validateUser(loginInfo, user);
        if (isValid) {
            progressDialog.show();
            initWebView();
            putOnServerRequest(loginInfo, user);
        }

    }


    void initView() {
        register = findViewById(R.id.id_userSignup_btn_register);
        fullName_et = findViewById(R.id.id_userSignup_et_name);
        email_et = findViewById(R.id.id_userSignup_et_email);
        mobileNum = findViewById(R.id.id_userSignup_et_mobile);
        password_et = findViewById(R.id.id_userSignup_et_password);
        confirmPassword_et = findViewById(R.id.id_userSignup_et_confirm_password);
        state_sp = findViewById(R.id.id_userSignup_sp_state);
        city_sp = findViewById(R.id.id_userSignup_sp_city);
        village_sp = findViewById(R.id.id_userSignup_sp_village);
        security_question_sp = findViewById(R.id.id_userSignup_sp_security_question);
        securityAnswer_et = findViewById(R.id.id_userSignup_et_security_answer);
        sarpanch_sw = findViewById(R.id.id_userSignup_sw_sarpanch);
    }

    boolean validateUser(UserLoginInfo loginInfo, User user) {
        if(VILLID == null) return false;
        if (!loginInfo.isValid(this)) return false;
        if (!isValid(user)) return false;
        return true;
    }

    private void putOnServerRequest(UserLoginInfo loginInfo, User user) {

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
                            //getting the user from the response to use it for ex- shared preferences //JSONObject userJson = obj.getJSONObject("user");
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
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", loginInfo.getUID());
                        params.put("user_name", loginInfo.getUSER_NAME());
                        params.put("email", loginInfo.getEMAIL());
                        params.put("password", loginInfo.getPASSWORD());
                        params.put("vill_id", VILLID);
                        params.put("security_question", loginInfo.getSECURITY_QUES());
                        params.put("security_answer", loginInfo.getSECURITY_ANS());
                        params.put("mobile", user.getMOBILE());
                        params.put("village", user.getVILLAGE());
                        params.put("city", user.getCITY());
                        params.put("state", user.getSTATE());
                        params.put("isSarpanch", String.valueOf(loginInfo.isSarpanch()));
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        webViewRequest.loadUrl(URLs.URL_REGISTER);
    }


    public void gotoLogin(View view){
        finish();
        startActivity(new Intent(this,UserLogin.class));
    }


    public boolean isValid(User user){
        String text = null;
        if(user.getEMAIL().isEmpty()){
            text = "Email";
        }
        else if(!user.getEMAIL().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            Toast.makeText(this, "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user.getMOBILE().isEmpty()){
            text = "Mobile Number";
        }
        else if(user.getMOBILE().length() != 10){
            Toast.makeText(this, "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user.getSTATE().isEmpty()) text = "State";
        else if(user.getCITY().isEmpty() || user.getCITY().equals("Select")) text = "City";
        else if(user.getVILLAGE().isEmpty() || user.getVILLAGE().equals("Select")) text = "Village";

        if(text != null){
            Toast.makeText(this, "Please Enter "+text, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
