package com.ok.Sarpanch.Department;

public class DepartmentItem {

    String department_id, department_name, department_head;
    String department_headID;

    public DepartmentItem(String department_id, String department_name, String department_head,String department_headID) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.department_head = department_head;
        this.department_headID = department_headID;
    }

    public String getDepartmentId() {
        return department_id;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public String getDepartmentHead() {
        return department_head;
    }

    public String getDepartment_headID() {
        return department_headID;
    }
}
