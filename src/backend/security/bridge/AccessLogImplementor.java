package backend.security.bridge;

import backend.security.service.SecurityRepository;
import backend.security.service.SecurityService;
import java.sql.SQLException;

public class AccessLogImplementor implements SecurityImplementor {

    private final SecurityRepository repository;

    public AccessLogImplementor() {
        this.repository = new SecurityRepository();
    }

    @Override
    public String encrypt(String data) {
        // Encryption delegated to EncryptionImplementor
        return data;
    }

    @Override
    public void log(String userId, String action, String tableName, String ipAddress) {
        try {
            // Log directly to audit_log table
            repository.saveAuditLog(userId, action, tableName, ipAddress);
        } catch (Exception e) {
            throw new RuntimeException("Failed to log access: " + e.getMessage());
        }
    }
}
