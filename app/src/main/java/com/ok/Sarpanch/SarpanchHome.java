package com.ok.Sarpanch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ok.Home.MemberList.MembersList;
import com.ok.Home.R;
import com.ok.Home.UserDetailsShow;
import com.ok.Home.UserDetailsUpdate;
import com.ok.Home.VillagersList;
import com.ok.Sarpanch.Department.Department;
import com.ok.Sarpanch.ManageServices.Services;

public class SarpanchHome extends AppCompatActivity {
    Button add_memberBtn;
    Button show_villagersBtn;
    Button show_membersBtn;
    Button manage_depatment, manage_services;
    Button myProfile, updateProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarpanch_home);
        //---------------
        init_view();
        //----------------
        add_memberBtn.setOnClickListener(v ->  callAddMember() );
        show_membersBtn.setOnClickListener(v -> callShowMembers());
        show_villagersBtn.setOnClickListener(v -> callShowVillagers());
        manage_depatment.setOnClickListener(v -> callManageDepartment());
        myProfile.setOnClickListener(v -> callMyProfile());
        updateProfile.setOnClickListener(v -> callUpdateDetails());
        manage_services.setOnClickListener(v -> callManageServices());
    }

    void init_view(){
        add_memberBtn = findViewById(R.id.id_sarpanch_add_member);
        show_villagersBtn = findViewById(R.id.id_sarpanch_show_villagers);
        show_membersBtn = findViewById(R.id.id_sarpanch_show_members);
        manage_depatment = findViewById(R.id.id_sarpanch_manage_department);
        myProfile = findViewById(R.id.id_sarpanch_btn_my_profile);
        updateProfile = findViewById(R.id.id_sarpanch_btn_updateMyDetails);
        manage_services = findViewById(R.id.id_sarpanch_manage_services);
    }

    void callUpdateDetails(){
        Intent intent = new Intent(this, UserDetailsUpdate.class);
        startActivity(intent);
    }


    void callMyProfile(){
        Intent intent = new Intent(this, UserDetailsShow.class);
        startActivity(intent);
    }

    void callManageDepartment(){
        Intent intent = new Intent(this, Department.class);
        startActivity(intent);
    }
    void callAddMember(){
        Intent intent = new Intent(this,AddMembers.class);
        startActivity(intent);
    }

    void callShowMembers(){
        Intent intent = new Intent(this, MembersList.class);
        startActivity(intent);
    }

    void callShowVillagers(){
        Intent intent = new Intent(this, VillagersList.class);
        startActivity(intent);
    }

    void callManageServices(){
        //add,delete,update services
        Intent intent = new Intent(this, Services.class);
        startActivity(intent);
    }

}
