package model;

import java.time.LocalDateTime;

public class AuditLog {

    private int logId;
    private User user;
    private String action;
    private String tableName;
    private String ipAddress;
    private LocalDateTime createdAt;

    // Constructors
    public AuditLog() {
    }

    public AuditLog(int logId, User user, String action, String tableName, String ipAddress, LocalDateTime createdAt) {
        this.logId = logId;
        this.user = user;
        this.action = action;
        this.tableName = tableName;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
