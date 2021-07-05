package com.ok.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ok.Home.R;
import com.ok.Login.UserSignup;

public class VerifyAdmin extends AppCompatActivity {

    EditText adminID, password;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);

        adminID = findViewById(R.id.id_admin_verify_et_adminID);
        password = findViewById(R.id.id_admin_verify_et_password);
        submit = findViewById(R.id.id_admin_verify_et_submit);

        submit.setOnClickListener(v -> {
            if(isValid()){
                login();
            }
        });
    }

    public void signUp(View view){
        Intent intent = new Intent(this, UserSignup.class);
        startActivity(intent);
    }

    boolean isValid(){
        if(adminID.getText().toString().trim().isEmpty()){
            adminID.requestFocus();
            adminID.setError("Enter Admin Id!");
            return false;
        }
        else if(password.getText().toString().trim().isEmpty()){
            password.requestFocus();
            password.setError("Enter Password!");
            return false;
        }
        else if(!adminID.getText().toString().equals("akshar") &&
                !password.getText().toString().equals("david_nishchal")){
            Toast.makeText(this, "Wrong ID or Password, Try again!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void login(){
        Intent intent = new Intent(this, AdminMain.class);
        startActivity(intent);
    }
}
