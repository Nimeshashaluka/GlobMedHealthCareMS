package backend.security.bridge;

public interface SecurityImplementor {

    String encrypt(String data);

    void log(String userId, String action, String tableName, String ipAddress);
}
