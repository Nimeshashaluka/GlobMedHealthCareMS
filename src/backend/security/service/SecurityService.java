package backend.security.service;

import backend.security.bridge.AccessLogImplementor;
import backend.security.bridge.RefinedSecurityAbstraction;
import backend.security.bridge.SecurityImplementor;
import backend.staffmanagement.composite.StaffComponent;
import backend.staffmanagement.service.StaffService;
import model.Staff;
import model.User;

public class SecurityService {

    private final SecurityImplementor securityImplementor;
    private final RefinedSecurityAbstraction securityAbstraction;
    private final UserService userService;
    private final StaffService staffService;

    public SecurityService() {
        this.securityImplementor = new AccessLogImplementor();
        this.securityAbstraction = new RefinedSecurityAbstraction(securityImplementor);
        this.userService = new UserService();
        this.staffService = new StaffService();
    }

    public String protectData(String data) {
        return securityAbstraction.protectData(data);
    }

    public void logAccess(String userId, String action, String tableName, String ipAddress) {
        securityAbstraction.logAccess(userId, action, tableName, ipAddress);
    }

    public StaffComponent authenticate(String username, String password, String ipAddress) {
        User user = userService.authenticate(username, password);
        if (user == null) {
            logAccess("unknown", "FAILED_LOGIN", "user", ipAddress);
            throw new SecurityException("Invalid username or password");
        }
        // Assume user_id corresponds to staff_id for simplicity
        Staff staff = staffService.getStaff(user.getUserId());
        if (staff == null) {
            logAccess(String.valueOf(user.getUserId()), "NO_STAFF_MAPPING", "user", ipAddress);
            throw new SecurityException("No staff profile found for user");
        }
        logAccess(String.valueOf(user.getUserId()), "SUCCESSFUL_LOGIN", "user", ipAddress);
        return staffService.createIndividualStaff(user.getUserId(), staff.getName(), user.getRole());
    }
    
}
