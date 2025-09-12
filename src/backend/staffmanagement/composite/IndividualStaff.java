package backend.staffmanagement.composite;

import backend.staffmanagement.service.StaffService;
import java.util.ArrayList;
import java.util.List;
import model.Role;

public class IndividualStaff implements StaffComponent {
    private final int staffId;
    private final String name;
    private final Role role;
    private final StaffService staffService;

    public IndividualStaff(int staffId, String name, Role role, StaffService staffService) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.staffService = staffService;
    }
    @Override
    public int getId() {
        return staffId;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public Role getRole() {
        return role;
    }
    @Override
    public void add(StaffComponent component) {
        throw new UnsupportedOperationException("Cannot add to an individual staff");
    }
    @Override
    public void remove(StaffComponent component) {
        throw new UnsupportedOperationException("Cannot remove from an individual staff");
    }
    @Override
    public List<StaffComponent> getChildren() {
        return new ArrayList<>();
    }
    @Override
    public boolean hasPermission(String permissionName) {
        return staffService.checkPermission(role.getRoleId(), permissionName);
    }
}
