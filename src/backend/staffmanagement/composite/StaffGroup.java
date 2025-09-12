package backend.staffmanagement.composite;
import model.Role;
import backend.staffmanagement.service.StaffService;
import java.util.ArrayList;
import java.util.List;

public class StaffGroup implements StaffComponent {
    private final int groupId;
    private final String name;
    private final Role role;
    private final StaffService staffService;
    private final List<StaffComponent> children = new ArrayList<>();

    public StaffGroup(int groupId, String name, Role role, StaffService staffService) {
        this.groupId = groupId;
        this.name = name;
        this.role = role;
        this.staffService = staffService;
    }
    @Override
    public int getId() {
        return groupId;
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
        children.add(component);
    }
    @Override
    public void remove(StaffComponent component) {
        children.remove(component);
    }
    @Override
    public List<StaffComponent> getChildren() {
        return children;
    }
    @Override
    public boolean hasPermission(String permissionName) {
        // Check group role permissions
        if (staffService.checkPermission(role.getRoleId(), permissionName)) {
            return true;
        }
        // Check permissions of children recursively
        for (StaffComponent child : children) {
            if (child.hasPermission(permissionName)) {
                return true;
            }
        }
        return false;
    }
}
