package com.ok.Home;

public class URLs {
    private static final String ROOT_SIGNUP_URL = "http://smartgramcapstone.42web.io/userRegistration.php?apicall=";
    public static final String URL_REGISTER = ROOT_SIGNUP_URL + "signup";
    public static final String URL_GET_STATES = ROOT_SIGNUP_URL + "getStates";
    public static final String URL_GET_CITIES = ROOT_SIGNUP_URL + "getCities";
    public static final String URL_GET_VILLAGES = ROOT_SIGNUP_URL + "getVillages";
    //----------------
    private static final String ROOT_SIGNIN_URL = "http://smartgramcapstone.42web.io/userLogin.php?apicall=";
    public static final String URL_LOGIN = ROOT_SIGNIN_URL + "signIn";

    private static final String ROOT_FORGET_PASSWORD_URL = "http://smartgramcapstone.42web.io/password_reset_token.php";
    public static final String FORGET_PASSWORD_URL = ROOT_FORGET_PASSWORD_URL + "";


    private static final String ROOT_VILLAGERS_URL = "http://smartgramcapstone.42web.io/Villagers.php?apicall=";
    public static final String URL_GET_VILLAGERS = ROOT_VILLAGERS_URL + "getVillagerList";
    public static final String URL_VILLAGER_TO_MEMBER = ROOT_VILLAGERS_URL + "villagerToMember";
    public static final String URL_GET_VILLAGER_PROFILE_LIST = ROOT_VILLAGERS_URL + "villagerProfileList";

    private static final String ROOT_MEMBERS_URL = "http://smartgramcapstone.42web.io/Members.php?apicall=";
    public static final String URL_GET_MEMBERS = ROOT_MEMBERS_URL + "getMemberList";
    public static final String URL_GET_REQUESTED_SERVICES = ROOT_MEMBERS_URL + "getRequestedServices";
    public static final String URL_GRANT_SERVICE = ROOT_MEMBERS_URL + "grantService";
    public static final String URL_REJECT_SERVICES = ROOT_MEMBERS_URL + "rejectService";
    public static final String URL_GET_SERVICE_TAKERS = ROOT_MEMBERS_URL + "getServiceTakers";
    public static final String URL_REVOKE_USER_SERVICE = ROOT_MEMBERS_URL + "revokeUserService";

    private static final String ROOT_USER_URL = "http://smartgramcapstone.42web.io/user.php?apicall=";
    public static final String URL_USER_PROFILE = ROOT_USER_URL + "userProfile";
    public static final String URL_USER_PROFILE_UPDATE = ROOT_USER_URL + "userProfileUpdate";
    public static final String URL_USER_REQUEST_SERVICE = ROOT_USER_URL + "requestService";
    public static final String URL_USER_SERVICES_LIST = ROOT_USER_URL + "getUserServicesList";
    public static final String URL_USER_GET_VILLAGE_SERVICES = ROOT_USER_URL + "getVillageServices";

    private static final String ROOT_SARPANCH_URL = "http://smartgramcapstone.42web.io/Sarpanch.php?apicall=";
    public static final String URL_ADD_DEPARTMENT = ROOT_SARPANCH_URL + "addDepartment";
    public static final String URL_DELETE_DEPARTMENT = ROOT_SARPANCH_URL + "deleteDepartment";
    public static final String URL_UPDATE_DEPARTMENT = ROOT_SARPANCH_URL + "updateDepartment";
    public static final String URL_GET_DEPARTMENT_INFO = ROOT_SARPANCH_URL + "getDepartmentInfo";
    public static final String URL_GET_SARPANCH_CONTACT_INFO = ROOT_SARPANCH_URL + "getSarpanchContact";
    //-----
    public static final String URL_ADD_SERVICE = ROOT_SARPANCH_URL + "addService";
    public static final String URL_DELETE_SERVICE = ROOT_SARPANCH_URL + "deleteService";
    public static final String URL_UPDATE_SERVICE = ROOT_SARPANCH_URL + "updateService";
    public static final String URL_GET_SERVICE_INFO = ROOT_SARPANCH_URL + "getServiceInfo";

    private static final String ROOT_ADMIN_URL = "http://smartgramcapstone.42web.io/Admin.php?apicall=";
    public static final String URL_APPROVE_USER = ROOT_ADMIN_URL + "approveUser";
    public static final String URL_REJECT_USER = ROOT_ADMIN_URL + "rejectUser";
    public static final String URL_GET_USER_FOR_VALIDATION = ROOT_ADMIN_URL + "getUserDetailsForVerification";

    private static final String ROOT_COMPLAINTS_URL = "http://smartgramcapstone.42web.io/complaints.php?apicall=";
    public static final String URL_COMPLAINTS_LIST_USER = ROOT_COMPLAINTS_URL + "getComplaintsListUser";
    public static final String URL_MAKE_COMPLAINT = ROOT_COMPLAINTS_URL + "makeComplaint";
    public static final String URL_COMPLAINTS_LIST_HEAD_WISE = ROOT_COMPLAINTS_URL + "getComplaintsListHeadWise";
    public static final String URL_COMPLAINTS_ON_SUBMIT_REMARKS = ROOT_COMPLAINTS_URL + "onSubmitRemarks";
    public static final String URL_COMPLAINTS_ON_CLOSE_COMPLAINT = ROOT_COMPLAINTS_URL + "onCloseComplaint";
    public static final String URL_CLOSED_COMPLAINTS_LIST_HEADWISE = ROOT_COMPLAINTS_URL + "getClosedComplaintsHeadWise";
}



/* 000webhost server
public class URLs {
    private static final String ROOT_SIGNUP_URL = "https://smartgrampanchayat.000webhostapp.com/userRegistration.php?apicall=";
    public static final String URL_REGISTER = ROOT_SIGNUP_URL + "signup";
    public static final String URL_GET_STATES = ROOT_SIGNUP_URL + "getStates";
    public static final String URL_GET_CITIES = ROOT_SIGNUP_URL + "getCities";
    public static final String URL_GET_VILLAGES = ROOT_SIGNUP_URL + "getVillages";
    //----------------
    private static final String ROOT_SIGNIN_URL = "https://smartgrampanchayat.000webhostapp.com/userLogin.php?apicall=";
    public static final String URL_LOGIN = ROOT_SIGNIN_URL + "signIn";

    private static final String ROOT_FORGET_PASSWORD_URL = "https://smartgrampanchayat.000webhostapp.com/password_reset_token.php";
    public static final String FORGET_PASSWORD_URL = ROOT_FORGET_PASSWORD_URL + "";


    private static final String ROOT_VILLAGERS_URL = "https://smartgrampanchayat.000webhostapp.com/Villagers.php?apicall=";
    public static final String URL_GET_VILLAGERS = ROOT_VILLAGERS_URL + "getVillagerList";
    public static final String URL_VILLAGER_TO_MEMBER = ROOT_VILLAGERS_URL + "villagerToMember";
    public static final String URL_GET_VILLAGER_PROFILE_LIST = ROOT_VILLAGERS_URL + "villagerProfileList";

    private static final String ROOT_MEMBERS_URL = "https://smartgrampanchayat.000webhostapp.com/Members.php?apicall=";
    public static final String URL_GET_MEMBERS = ROOT_MEMBERS_URL + "getMemberList";
    public static final String URL_GET_REQUESTED_SERVICES = ROOT_MEMBERS_URL + "getRequestedServices";
    public static final String URL_GRANT_SERVICE = ROOT_MEMBERS_URL + "grantService";
    public static final String URL_REJECT_SERVICES = ROOT_MEMBERS_URL + "rejectService";
    public static final String URL_GET_SERVICE_TAKERS = ROOT_MEMBERS_URL + "getServiceTakers";
    public static final String URL_REVOKE_USER_SERVICE = ROOT_MEMBERS_URL + "revokeUserService";

    private static final String ROOT_USER_URL = "https://smartgrampanchayat.000webhostapp.com/user.php?apicall=";
    public static final String URL_USER_PROFILE = ROOT_USER_URL + "userProfile";
    public static final String URL_USER_PROFILE_UPDATE = ROOT_USER_URL + "userProfileUpdate";
    public static final String URL_USER_REQUEST_SERVICE = ROOT_USER_URL + "requestService";
    public static final String URL_USER_SERVICES_LIST = ROOT_USER_URL + "getUserServicesList";
    public static final String URL_USER_GET_VILLAGE_SERVICES = ROOT_USER_URL + "getVillageServices";

    private static final String ROOT_SARPANCH_URL = "https://smartgrampanchayat.000webhostapp.com/Sarpanch.php?apicall=";
    public static final String URL_ADD_DEPARTMENT = ROOT_SARPANCH_URL + "addDepartment";
    public static final String URL_DELETE_DEPARTMENT = ROOT_SARPANCH_URL + "deleteDepartment";
    public static final String URL_UPDATE_DEPARTMENT = ROOT_SARPANCH_URL + "updateDepartment";
    public static final String URL_GET_DEPARTMENT_INFO = ROOT_SARPANCH_URL + "getDepartmentInfo";
    public static final String URL_GET_SARPANCH_CONTACT_INFO = ROOT_SARPANCH_URL + "getSarpanchContact";
    //-----
    public static final String URL_ADD_SERVICE = ROOT_SARPANCH_URL + "addService";
    public static final String URL_DELETE_SERVICE = ROOT_SARPANCH_URL + "deleteService";
    public static final String URL_UPDATE_SERVICE = ROOT_SARPANCH_URL + "updateService";
    public static final String URL_GET_SERVICE_INFO = ROOT_SARPANCH_URL + "getServiceInfo";

    private static final String ROOT_ADMIN_URL = "https://smartgrampanchayat.000webhostapp.com/Admin.php?apicall=";
    public static final String URL_APPROVE_USER = ROOT_ADMIN_URL + "approveUser";
    public static final String URL_REJECT_USER = ROOT_ADMIN_URL + "rejectUser";
    public static final String URL_GET_USER_FOR_VALIDATION = ROOT_ADMIN_URL + "getUserDetailsForVerification";

    private static final String ROOT_COMPLAINTS_URL = "https://smartgrampanchayat.000webhostapp.com/complaints.php?apicall=";
    public static final String URL_COMPLAINTS_LIST_USER = ROOT_COMPLAINTS_URL + "getComplaintsListUser";
    public static final String URL_MAKE_COMPLAINT = ROOT_COMPLAINTS_URL + "makeComplaint";
    public static final String URL_COMPLAINTS_LIST_HEAD_WISE = ROOT_COMPLAINTS_URL + "getComplaintsListHeadWise";
    public static final String URL_COMPLAINTS_ON_SUBMIT_REMARKS = ROOT_COMPLAINTS_URL + "onSubmitRemarks";
    public static final String URL_COMPLAINTS_ON_CLOSE_COMPLAINT = ROOT_COMPLAINTS_URL + "onCloseComplaint";
    public static final String URL_CLOSED_COMPLAINTS_LIST_HEADWISE = ROOT_COMPLAINTS_URL + "getClosedComplaintsHeadWise";
}

*/
