package backend.security.bridge;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionImplementor implements SecurityImplementor {

    private static final String KEY = "MySecretKey12345";
    private static final String ALGORITHM = "AES";

    @Override
    public String encrypt(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage());
        }
    }

    @Override
    public void log(String userId, String action, String tableName, String ipAddress) {
        // Logging delegated to AccessLogImplementor
    }
}
