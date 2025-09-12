package model;

public class Permission {

    private int permissionId;
    private String name;

    // Constructors
    public Permission() {
    }

    public Permission(int permissionId, String name) {
        this.permissionId = permissionId;
        this.name = name;
    }

    // Getters and Setters
    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
