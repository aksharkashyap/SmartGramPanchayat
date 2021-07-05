package com.ok.Home;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayUserProfile extends AppCompatActivity {
    User user;
    TextView UID, VILL_ID;
    TextView MOBILE, EMAIL;
    TextView VILLAGE, CITY, STATE;
    TextView NAME, GENDER;
    TextView ADHAR, PAN, VOTER_ID;
    TextView DOB, BLOOD_GROUP;
    TextView FATHER,MOTHER;
    TextView MARITAL_STATUS;
    TextView MEMBER_COUNT;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);
        getUser();
        init_view();
        set_text();
    }

    void getUser(){
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    public void callUpdateDetails(View view){
        Intent intent = new Intent(this,UserDetailsUpdate.class);
        startActivity(intent);
    }

    void init_view(){
        UID = findViewById(R.id.id_display_user_profile_UID);
        VILL_ID = findViewById(R.id.id_display_user_profile_VILL_ID);
        MOBILE = findViewById(R.id.id_display_user_profile_MOBILE);
        EMAIL = findViewById(R.id.id_display_user_profile_EMAIL);
        VILLAGE = findViewById(R.id.id_display_user_profile_VILLAGE);
        CITY = findViewById(R.id.id_display_user_profile_CITY);
        STATE = findViewById(R.id.id_display_user_profile_STATE);
        NAME = findViewById(R.id.id_display_user_profile_NAME);
        GENDER = findViewById(R.id.id_display_user_profile_GENDER);
        ADHAR = findViewById(R.id.id_display_user_profile_ADHAR);
        PAN = findViewById(R.id.id_display_user_profile_PAN);
        VOTER_ID = findViewById(R.id.id_display_user_profile_VOTER_ID);
        DOB = findViewById(R.id.id_display_user_profile_DOB);
        BLOOD_GROUP = findViewById(R.id.id_display_user_profile_BLOOD_GROUP);
        FATHER = findViewById(R.id.id_display_user_profile_FATHER);
        MOTHER = findViewById(R.id.id_display_user_profile_MOTHER);
        MARITAL_STATUS = findViewById(R.id.id_display_user_profile_MERITAL_STATUS);
        MEMBER_COUNT = findViewById(R.id.id_display_user_profile_MEMBER_COUNT);
    }

    void set_text(){
        UID.setText("User ID: " + user.getUID());
        VILL_ID.setText("Village ID: " +user.getVILL_ID());
        MOBILE.setText("Mobile: " +user.getMOBILE());
        EMAIL.setText("Email: " +user.getEMAIL());
        VILLAGE.setText("Village: " +user.getVILLAGE());
        CITY.setText("City: " +user.getCITY());
        STATE.setText("State: " +user.getSTATE());
        NAME.setText("Name: " +user.getNAME());
        GENDER.setText("Gender: " +user.getGENDER());
        ADHAR.setText("Adhar: " +user.getADHAR());
        PAN.setText("Pan: " +user.getPAN());
        VOTER_ID.setText("Voter ID: " +user.getVOTER_ID());
        DOB.setText("DOB: " +user.getDOB());
        BLOOD_GROUP.setText("Blood Group: " +user.getBLOOD_GROUP());
        FATHER.setText("Father: " +user.getFATHER());
        MOTHER.setText("Mother: " +user.getMOTHER());
        MARITAL_STATUS.setText("Marital Status: " +user.getMARITAL_STATUS());
        MEMBER_COUNT.setText("Total Members: " +user.getMEMBER_COUNT());

    }
}
