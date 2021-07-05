package com.ok.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ok.Admin.UserValidation.ValidateNewUser;
import com.ok.Home.R;

public class AdminMain extends AppCompatActivity {

    Button validateNewUserBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init_view();

        Intent Intent_validateNewUser = new Intent(getApplicationContext(), ValidateNewUser.class);
        validateNewUserBtn.setOnClickListener(v -> startActivity(Intent_validateNewUser));
    }

    void init_view(){
        validateNewUserBtn = findViewById(R.id.id_admin_main_btn_validateNewUser);
    }

    void getSarpanchInfo(){
        //spinner state,city,village
        //show admin -> name,state,city,village,mobile,email
    }
}
