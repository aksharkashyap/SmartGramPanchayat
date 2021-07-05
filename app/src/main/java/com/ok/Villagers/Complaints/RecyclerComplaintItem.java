package com.ok.Villagers.Complaints;

public class RecyclerComplaintItem {
    String complaintID, department, head, complaintMSG;
    String complaintStatus, remarksByHead;
    String complaintDate, lastStatusDate;



    public RecyclerComplaintItem(String complaintID, String department, String head, String complaintMSG,
                                 String complaintStatus, String remarksByHead, String complaintDate,
                                 String lastStatusDate)
    {
        this.complaintID = complaintID;
        this.department = department;
        this.head = head;
        this.complaintMSG = complaintMSG;
        this.complaintStatus = complaintStatus;
        this.remarksByHead = remarksByHead;
        this.complaintDate = complaintDate;
        this.lastStatusDate = lastStatusDate;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public String getDepartment() {
        return department;
    }

    public String getHead() {
        return head;
    }

    public String getComplaintMSG() {
        return complaintMSG;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    } //active, closed

    public String getRemarksByHead() {
        return remarksByHead;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public String getLastStatusDate() {
        return lastStatusDate;
    }
}
