package com.ok.Admin.UserValidation;

public class RecyclerValidateUserItem {

    String user_id, vill_id, email, mobile, user_name;
    String village, city, state, verification_status;

    public RecyclerValidateUserItem(String user_id, String vill_id,
                                    String email, String mobile,
                                    String user_name, String village,
                                    String city, String state,
                                    String verification_status
    ) {
        this.user_id = user_id;
        this.vill_id = vill_id;
        this.email = email;
        this.mobile = mobile;
        this.user_name = user_name;
        this.village = village;
        this.city = city;
        this.state = state;
        this.verification_status = verification_status;
    }

    public String getUserID() {
        return user_id;
    }

    public String getVillID() {
        return vill_id;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUserName() {
        return user_name;
    }

    public String getVillage() {
        return village;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getVerificationStatus() {
        return verification_status;
    }
}
