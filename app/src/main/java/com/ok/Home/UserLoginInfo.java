package com.ok.Home;

import android.content.Context;
import android.widget.Toast;

public class UserLoginInfo {
    private String UID, USER_NAME, VILL_ID;
    private String EMAIL, PASSWORD, CONFIRM_PASS;
    private  String SECURITY_QUES, SECURITY_ANS;
    private  boolean isSarpanch;
    private int STATUS; //is logged in user sarpanch,member or villager(0,1,2)

    public UserLoginInfo(String UID,String USER_NAME, String EMAIL, String PASSWORD,String CONFIRM_PASS, String SECURITY_QUES,
                         String SECURITY_ANS, boolean isSarpanch) {
        this.UID = UID;
        this.USER_NAME = USER_NAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.CONFIRM_PASS = CONFIRM_PASS;
        this.SECURITY_QUES = SECURITY_QUES;
        this.SECURITY_ANS = SECURITY_ANS;
        this.isSarpanch = isSarpanch;
    }

    public UserLoginInfo(String UID, String EMAIL, int STATUS, String VILL_ID) {
        this.UID = UID;
        this.EMAIL = EMAIL;
        this.STATUS = STATUS;
        this.VILL_ID = VILL_ID;
    }

    public String getVILL_ID() {
        return VILL_ID;
    }

    public String getUID() {
        return UID;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getSECURITY_QUES(){return SECURITY_QUES;}

    public String getSECURITY_ANS(){return SECURITY_ANS;}

    public String getUSER_NAME(){return USER_NAME;}

    public boolean isSarpanch(){return isSarpanch;}

    public boolean isValid(Context context){
        String text = null;
        if(USER_NAME.isEmpty()){
            text = "User Name";
        }
        else if(USER_NAME.length() > 29){
            Toast.makeText(context, "Full Name length should be less than 30", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(EMAIL.isEmpty()){
            text = "Email";
        }
        else if(!EMAIL.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            Toast.makeText(context, "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(PASSWORD.isEmpty()){
            text = "Password";
        }
        else if(PASSWORD.length() < 5 || PASSWORD.length() > 21){
            Toast.makeText(context, "Password length should be [6,20]", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(CONFIRM_PASS.isEmpty()){
            text = "Confirm Password";
        }
        else if(!PASSWORD.equals(CONFIRM_PASS)){
            Toast.makeText(context, "Password mismatch!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(SECURITY_ANS.isEmpty()){
            text = "Security Answer";
        }
        else if(SECURITY_ANS.length() < 5 || SECURITY_ANS.length() > 21){
            Toast.makeText(context, "Security Answer length should be [6,20]", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(text != null){
            Toast.makeText(context, "Please Enter "+ text, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
