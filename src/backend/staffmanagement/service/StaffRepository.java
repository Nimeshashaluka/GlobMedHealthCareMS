package backend.staffmanagement.service;

import model.Staff;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import util.MySQL;

public class StaffRepository {

    public Staff getStaff(int staffId) {
        try {
            String query = "SELECT s.staff_id, s.name, s.specialization, s.contact_info, u.role_id, r.name AS role_name "
                    + "FROM staff s JOIN user u ON s.staff_id = u.user_id JOIN role r ON u.role_id = r.role_id "
                    + "WHERE s.staff_id = ?";
            ResultSet rs = MySQL.executeQuery(query, staffId);
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("name"));
                staff.setSpecialization(rs.getString("specialization"));
                staff.setContactInfo(rs.getString("contact_info"));
                staff.setRoleId(rs.getInt("role_id"));
                staff.setRoleName(rs.getString("role_name"));
                rs.close();
                return staff;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve staff: " + e.getMessage());
        }
    }

    public List<Staff> getAllStaff() {
        try {
            String query = "SELECT s.staff_id, s.name, s.specialization, s.contact_info,s.created_at, u.role_id, r.name AS role_name "
                    + "FROM staff s JOIN user u ON s.staff_id = u.user_id JOIN role r ON u.role_id = r.role_id";
            ResultSet rs = MySQL.executeQuery(query);
            List<Staff> staffList = new ArrayList<>();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("name"));
                staff.setSpecialization(rs.getString("specialization"));
                staff.setContactInfo(rs.getString("contact_info"));
                staff.setRoleId(rs.getInt("role_id"));
                staff.setRoleName(rs.getString("role_name"));
                staff.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                staffList.add(staff);
            }
            rs.close();
            return staffList;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve staff list: " + e.getMessage());
        }
    }

    public List<Staff> getAllDoctors() {
        try {
            String query = "SELECT s.staff_id, s.name, s.specialization, s.contact_info, u.role_id, r.name AS role_name "
                    + "FROM staff s JOIN user u ON s.staff_id = u.user_id JOIN role r ON u.role_id = r.role_id "
                    + "WHERE u.role_id = 2";
            ResultSet rs = MySQL.executeQuery(query);
            List<Staff> staffList = new ArrayList<>();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("name"));
                staff.setSpecialization(rs.getString("specialization"));
                staff.setContactInfo(rs.getString("contact_info"));
                staff.setRoleId(rs.getInt("role_id"));
                staff.setRoleName(rs.getString("role_name"));
                staffList.add(staff);
            }
            rs.close();
            return staffList;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve doctors: " + e.getMessage());
        }
    }

    public void saveStaff(Staff staff, String username, String password, int roleId) {
        try {
            String userQuery = "INSERT INTO user (username, password, role_id,created_at,updated_at) VALUES (?, ?, ?,NOW(),NOW())";
            int userId = MySQL.executeUpdateWithKeys(userQuery, username, password, roleId);
            String staffQuery = "INSERT INTO staff (staff_id,name, specialization, contact_info, created_at) VALUES (?,?, ?, ?, NOW())";
            MySQL.executeUpdate(staffQuery, userId, staff.getName(), staff.getSpecialization(), staff.getContactInfo());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save staff: " + e.getMessage());
        }
    }

    public boolean hasPermission(int roleId, String permissionName) {
        try {
            String query = "SELECT 1 FROM role_permission rp "
                    + "JOIN permission p ON rp.permission_id = p.permission_id "
                    + "WHERE rp.role_id = ? AND p.name = ?";
            ResultSet rs = MySQL.executeQuery(query, roleId, permissionName);
            boolean hasPermission = rs.next();
            rs.close();
            return hasPermission;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check permission: " + e.getMessage());
        }
    }

    public List<Role> getAllRoles() {
        try {
            String query = "SELECT role_id, name FROM role";
            ResultSet rs = MySQL.executeQuery(query);
            List<Role> roles = new ArrayList<>();
            while (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("name"));
                roles.add(role);
            }
            rs.close();
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve roles: " + e.getMessage());
        }
    }

    public boolean usernameExists(String username) {
        try {
            String query = "SELECT 1 FROM user WHERE username = ?";
            ResultSet rs = MySQL.executeQuery(query, username);
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check username: " + e.getMessage());
        }
    }
}
