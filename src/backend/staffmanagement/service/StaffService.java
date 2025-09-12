package backend.staffmanagement.service;

import backend.staffmanagement.composite.StaffComponent;
import backend.staffmanagement.composite.IndividualStaff;
import backend.staffmanagement.composite.StaffGroup;
import java.util.List;
import model.Role;
import model.Staff;

public class StaffService {
    
    private final StaffRepository repository;
    
    public StaffService() {
        this.repository = new StaffRepository();
    }
    public StaffComponent createIndividualStaff(int staffId, String name, Role role) {
        return new IndividualStaff(staffId, name, role, this);
    }
    public StaffComponent createStaffGroup(int groupId, String name, Role role) {
        return new StaffGroup(groupId, name, role, this);
    }
    public boolean checkPermission(int roleId, String permissionName) {
        return repository.hasPermission(roleId, permissionName);
    }
    
    public Staff getStaff(int staffId) {
        return repository.getStaff(staffId);
    }
    public List<Staff> getAllStaff() {
        return repository.getAllStaff();
    }
    public List<Staff> getAllDoctors() {
        return repository.getAllDoctors();
    }
    
    public void saveStaff(Staff staff, String username, String password, int roleId) {
        repository.saveStaff(staff, username, password, roleId);
    }
    
    public List<Role> getAllRoles() {
        return repository.getAllRoles();
    }

    public boolean usernameExists(String username) {
        return repository.usernameExists(username);
    }
    
}
