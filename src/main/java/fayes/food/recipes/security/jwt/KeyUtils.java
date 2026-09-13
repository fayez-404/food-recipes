package fayes.food.recipes.security.jwt;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

    public static String readKeyFromResources(String path){
        try(InputStream is = KeyUtils.class.getClassLoader().getResourceAsStream(path)){
            return new String(is.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WHILE READING THE KEYS");
        }
    }

    public static PrivateKey loadPrivateKey(String path) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String key = readKeyFromResources(path).replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s","");
        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("rsa").generatePrivate(spec);

    }
    public static PublicKey loadPublicKey(String path) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String key = readKeyFromResources(path).replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s","");
        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("rsa").generatePublic(spec);
    }

}
