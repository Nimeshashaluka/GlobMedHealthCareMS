package backend.security.bridge;

public interface SecurityAbstraction {

    String protectData(String data);

    void logAccess(String userId, String action, String tableName, String ipAddress);
}
