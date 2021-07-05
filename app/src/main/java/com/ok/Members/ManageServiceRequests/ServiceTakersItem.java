package com.ok.Members.ManageServiceRequests;

public class ServiceTakersItem {

    private String serviceName, serviceID;
    private String applicantName, applicantID;
    private String mobile;

    public ServiceTakersItem(String serviceName, String serviceID, String applicantName, String applicantID, String mobile) {
        this.serviceName = serviceName;
        this.serviceID = serviceID;
        this.applicantName = applicantName;
        this.applicantID = applicantID;
        this.mobile = mobile;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getMobile() {
        return mobile;
    }
}

