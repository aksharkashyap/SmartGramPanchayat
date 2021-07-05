package com.ok.Members.Complaints;

public class MemberComplaintItem {
    String complaintID, department, head, complaintMSG;
    String complaintStatus, remarksByHead;
    String complaintDate, lastStatusDate;
    String applicantID, applicantName;


    public MemberComplaintItem(String complaintID, String department, String head, String complaintMSG,
                                 String complaintStatus, String remarksByHead, String complaintDate,
                                 String lastStatusDate,String applicantID, String applicantName)
    {
        this.complaintID = complaintID;
        this.department = department;
        this.head = head;
        this.complaintMSG = complaintMSG;
        this.complaintStatus = complaintStatus;
        this.remarksByHead = remarksByHead;
        this.complaintDate = complaintDate;
        this.lastStatusDate = lastStatusDate;
        this.applicantID = applicantID;
        this.applicantName = applicantName;
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

    public String getApplicantID() {
        return applicantID;
    }

    public String getApplicantName() {
        return applicantName;
    }
}
