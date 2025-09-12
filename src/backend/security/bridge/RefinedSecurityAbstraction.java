package backend.security.bridge;

public class RefinedSecurityAbstraction implements SecurityAbstraction {

    private final SecurityImplementor implementor;

    public RefinedSecurityAbstraction(SecurityImplementor implementor) {
        this.implementor = implementor;
    }

    @Override
    public String protectData(String data) {
        return implementor.encrypt(data);
    }

    @Override
    public void logAccess(String userId, String action, String tableName, String ipAddress) {
        implementor.log(userId, action, tableName, ipAddress);
    }
}
