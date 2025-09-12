package backend.staffmanagement.composite;

import java.util.List;
import model.Role;

public interface StaffComponent {

    int getId();

    String getName();

    Role getRole();

    void add(StaffComponent component);

    void remove(StaffComponent component);

    List<StaffComponent> getChildren();

    boolean hasPermission(String permissionName);
}
