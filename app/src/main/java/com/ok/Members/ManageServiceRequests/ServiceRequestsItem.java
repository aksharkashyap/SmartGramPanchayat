package com.ok.Members.ManageServiceRequests;

public class ServiceRequestsItem {

    String serviceName, serviceID;
    String applicantName, applicantID;

    public ServiceRequestsItem(String serviceName, String serviceID, String applicantName, String applicantID) {
        this.serviceName = serviceName;
        this.serviceID = serviceID;
        this.applicantName = applicantName;
        this.applicantID = applicantID;
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
}
