package backend.security.service;

import model.Role;
import model.User;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public User authenticate(String username, String password) {
        try {
            String query = "SELECT u.user_id, u.username, u.role_id, r.name AS role_name, u.created_at, u.updated_at "
                    + "FROM user u JOIN role r ON u.role_id = r.role_id WHERE u.username = ? AND u.password = ?";
            // Note: In production, use hashed passwords (e.g., BCrypt)
            ResultSet rs = MySQL.executeQuery(query, username, password);
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(new Role(rs.getInt("role_id"), rs.getString("role_name")));
                user.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
                user.setUpdatedAt(rs.getObject("updated_at", java.time.LocalDateTime.class));
                rs.close();
                return user;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}
