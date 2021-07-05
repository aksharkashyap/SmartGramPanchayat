package com.ok.Villagers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ok.Home.MemberList.MembersList;
import com.ok.Home.R;
import com.ok.Home.SarpanchContactDetails;
import com.ok.Home.UserDetailsShow;
import com.ok.Home.UserDetailsUpdate;
import com.ok.Villagers.Complaints.ManageComplaint;
import com.ok.Villagers.Services.MyServices;
import com.ok.Villagers.Services.RequestService;

public class VillagerHome extends AppCompatActivity {

    Button myDetailsBtn, updateDetailsBtn, sarpanchContactBtn, showVillageMembers;
    Button complaints;
    Button requestServiceBtn, myServicesBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villager_home);

        myDetailsBtn = findViewById(R.id.id_villager_btn_showMyDetails);
        sarpanchContactBtn = findViewById(R.id.id_villager_btn_sarpanchContact);
        showVillageMembers = findViewById(R.id.id_villager_btn_showVillageMembers);
        updateDetailsBtn = findViewById(R.id.id_villager_btn_updateMyDetails);
        complaints = findViewById(R.id.id_villager_btn_complaints);
        requestServiceBtn = findViewById(R.id.id_villager_btn_requestService);
        myServicesBtn = findViewById(R.id.id_villager_btn_myServices);

        myDetailsBtn.setOnClickListener(v -> callMyProfile());
        updateDetailsBtn.setOnClickListener(v -> callUpdateDetails());
        sarpanchContactBtn.setOnClickListener(v -> callSarpanchContact());
        showVillageMembers.setOnClickListener(v -> showVillageMembers());
        complaints.setOnClickListener(v -> complaints());
        requestServiceBtn.setOnClickListener(v -> requestService());
        myServicesBtn.setOnClickListener(v -> myServices());
    }

    void callUpdateDetails(){
        Intent intent = new Intent(this, UserDetailsUpdate.class);
        startActivity(intent);
    }

    void callMyProfile(){
        Intent intent = new Intent(this, UserDetailsShow.class);
        startActivity(intent);
    }

    void callSarpanchContact(){
        Intent intent = new Intent(this, SarpanchContactDetails.class);
        startActivity(intent);
    }

    void showVillageMembers(){
        Intent intent = new Intent(this, MembersList.class);
        startActivity(intent);
    }

    void requestService(){
        Intent intent = new Intent(this, RequestService.class);
        startActivity(intent);
    }

    void myServices(){
        Intent intent = new Intent(this, MyServices.class);
        startActivity(intent);
    }

    void complaints(){
        Intent intent = new Intent(this, ManageComplaint.class);
        startActivity(intent);
    }
}
