package com.ok.Members;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ok.Home.MemberList.MembersList;
import com.ok.Home.R;
import com.ok.Home.SarpanchContactDetails;
import com.ok.Home.UserDetailsShow;
import com.ok.Home.UserDetailsUpdate;
import com.ok.Home.VillagersList;
import com.ok.Members.Complaints.ActiveComplaints;
import com.ok.Members.Complaints.ClosedComplaints;
import com.ok.Members.ManageServiceRequests.ServiceRequests;
import com.ok.Members.ManageServiceRequests.ServiceTakers;

public class MemberHome extends AppCompatActivity {

    Button show_villagersBtn, sarpanchContactInfo, myProfile, updateProfile, show_villageMembersBtn;
    Button active_complaints, closed_complaints,manageServiceRequests;
    Button serviceTakersBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);
        show_villagersBtn = findViewById(R.id.id_member_home_btn_showVillagers);
        sarpanchContactInfo = findViewById(R.id.id_member_home_btn_sarpanchContactInfo);
        myProfile = findViewById(R.id.id_member_home_btn_myProfile);
        updateProfile = findViewById(R.id.id_member_home_btn_updateMyDetails);
        show_villageMembersBtn = findViewById(R.id.id_member_home_btn_showVillageMembers);

        manageServiceRequests = findViewById(R.id.id_member_home_btn_manageServiceRequests);
        serviceTakersBtn = findViewById(R.id.id_member_home_btn_manageServiceTakers);

        active_complaints = findViewById(R.id.id_member_home_btn_ActiveComplaints);
        closed_complaints = findViewById(R.id.id_member_home_btn_ClosedComplaints);

        show_villagersBtn.setOnClickListener(v -> callShowVillagers());
        sarpanchContactInfo.setOnClickListener(v -> sarpanchContactDetails());
        myProfile.setOnClickListener(v -> callMyProfile());
        updateProfile.setOnClickListener(v -> callUpdateDetails());
        show_villageMembersBtn.setOnClickListener(v -> callShowVillageMembers());
        active_complaints.setOnClickListener(v -> gotoActiveComplaints());
        closed_complaints.setOnClickListener(v -> gotoClosedComplaints());
        manageServiceRequests.setOnClickListener(v -> gotoManageServiceRequests());
        serviceTakersBtn.setOnClickListener(v -> gotoServiceTakers());

    }

    public void callUpdateDetails(){
        Intent intent = new Intent(this, UserDetailsUpdate.class);
        startActivity(intent);
    }

    public void callMyProfile(){
        Intent intent = new Intent(this, UserDetailsShow.class);
        startActivity(intent);
    }

    public void callShowVillagers(){
        Intent intent = new Intent(this, VillagersList.class);
        startActivity(intent);
    }

    public void callShowVillageMembers(){
        Intent intent = new Intent(this, MembersList.class);
        startActivity(intent);
    }

    public void sarpanchContactDetails(){
        Intent intent = new Intent(this, SarpanchContactDetails.class);
        startActivity(intent);
    }

    public  void gotoActiveComplaints(){
        Intent intent = new Intent(this, ActiveComplaints.class);
        startActivity(intent);
    }

    public void gotoClosedComplaints(){
        Intent intent = new Intent(this, ClosedComplaints.class);
        startActivity(intent);
    }

    public void gotoManageServiceRequests(){
        Intent intent = new Intent(this, ServiceRequests.class);
        startActivity(intent);
    }

    public void gotoServiceTakers(){
        Intent intent = new Intent(this, ServiceTakers.class);
        startActivity(intent);
    }


}

