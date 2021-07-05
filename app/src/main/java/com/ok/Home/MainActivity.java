package com.ok.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ok.Admin.AdminMain;
import com.ok.Admin.VerifyAdmin;
import com.ok.Login.UserForgetPassword;
import com.ok.Login.UserLogin;
import com.ok.Login.UserSignup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
    }

    public void login(View view){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, UserSignup.class);
        startActivity(intent);
    }

    public void forgetPassword(View view){
        Intent intent = new Intent(this, UserForgetPassword.class);
        startActivity(intent);
    }

    public void callAdmin(View view){
        Intent intent = new Intent(this, VerifyAdmin.class);
        startActivity(intent);
    }
}