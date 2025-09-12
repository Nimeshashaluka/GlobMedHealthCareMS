package backend.security.service;

import util.MySQL;
import model.AuditLog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.User;

public class SecurityRepository {

    public void saveAuditLog(String userId, String action, String tableName, String ipAddress) throws SQLException {
        String query = "INSERT INTO audit_log (user_id, action, table_name, ip_address, created_at) VALUES (?, ?, ?, ?, NOW())";
        // Pass null for userId if it's "unknown" or null
        Object userIdParam = (userId == null || "unknown".equals(userId)) ? null : userId;
        MySQL.executeUpdate(query, userIdParam, action, tableName, ipAddress);
    }

    public AuditLog getAuditLog(int logId) {
        try {
            String query = "SELECT * FROM audit_log WHERE log_id = ?";
            ResultSet rs = MySQL.executeQuery(query, logId);
            if (rs.next()) {
                AuditLog log = new AuditLog();
                log.setLogId(rs.getInt("log_id"));
                log.setUser(new User());
                log.setAction(rs.getString("action"));
                log.setTableName(rs.getString("table_name"));
                log.setIpAddress(rs.getString("ip_address"));
                log.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                rs.close(); // Close ResultSet
                return log;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve audit log: " + e.getMessage());
        }
    }
}
