package com.ok.Villagers.Services;

public class VillagerServiceItem {

    String serviceID, serviceName;

    public VillagerServiceItem(String serviceID, String serviceName) {
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
