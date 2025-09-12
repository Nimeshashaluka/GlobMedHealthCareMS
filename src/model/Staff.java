package model;

import java.time.LocalDateTime;

public class Staff {

    private int staffId;
    private String name;
    private String specialization;
    private String contactInfo;
    private LocalDateTime createdAt;
    private int roleId;
    private String roleName;

    // Constructors
    public Staff() {
    }

    public Staff(int staffId, String name, String specialization, String contactInfo, LocalDateTime createdAt) {
        this.staffId = staffId;
        this.name = name;
        this.specialization = specialization;
        this.contactInfo = contactInfo;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
