package com.ok.Sarpanch.ManageServices;

public class ServiceItem {

    String serviceID, serviceName;

    public ServiceItem(String serviceID, String serviceName) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }
}
