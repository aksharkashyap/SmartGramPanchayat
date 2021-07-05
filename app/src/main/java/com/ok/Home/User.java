package com.ok.Home;

import java.io.Serializable;

public class User implements Serializable {
    private String UID, VILL_ID;
    private String MOBILE, EMAIL;
    private String VILLAGE, CITY, STATE;
    private String NAME, GENDER;
    private String ADHAR, PAN, VOTER_ID;
    private String DOB, BLOOD_GROUP;
    private String FATHER,MOTHER;
    private String MARITAL_STATUS;
    private String MEMBER_COUNT;


    public void personalDetailsSetter(String UID, String VILL_ID, String NAME, String GENDER,
                                      String ADHAR, String PAN, String VOTER_ID,
                                      String DOB, String BLOOD_GROUP,String MARITAL_STATUS){
        this.UID = UID;
        this.VILL_ID = VILL_ID;
        this.NAME = NAME;
        this.GENDER = GENDER;
        this.ADHAR = ADHAR;
        this.PAN = PAN;
        this.VOTER_ID = VOTER_ID;
        this.DOB = DOB;
        this.BLOOD_GROUP = BLOOD_GROUP;
        this.MARITAL_STATUS = MARITAL_STATUS;
    }

    public void contactDetailsSetter(String MOBILE, String EMAIL,
                                     String VILLAGE, String CITY, String STATE){
        this.MOBILE = MOBILE;
        this.EMAIL = EMAIL;
        this.VILLAGE = VILLAGE;
        this.CITY = CITY;
        this.STATE = STATE;
    }

    public void familyDetailsSetter( String FATHER, String MOTHER,String MEMBER_COUNT){
        this.FATHER = FATHER;
        this.MOTHER = MOTHER;
        this.MEMBER_COUNT = MEMBER_COUNT;
    }


    public String getUID() {
        return UID;
    }

    public String getVILL_ID() {
        return VILL_ID;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getVILLAGE() {
        return VILLAGE;
    }

    public String getCITY() {
        return CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getADHAR() {
        return ADHAR;
    }

    public String getPAN() {
        return PAN;
    }

    public String getVOTER_ID() {
        return VOTER_ID;
    }

    public String getDOB() {
        return DOB;
    }

    public String getBLOOD_GROUP() {
        return BLOOD_GROUP;
    }

    public String getFATHER() {
        return FATHER;
    }

    public String getMOTHER() {
        return MOTHER;
    }

    public String getMARITAL_STATUS() {
        return MARITAL_STATUS;
    }

    public String getMEMBER_COUNT() {
        return MEMBER_COUNT;
    }
}
