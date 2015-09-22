package challenges.sutrix.androidappnetclient.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by NgoVD on 9/16/15.
 *
 */
public class SecurityUtils {

//    private static String mSecretKey = "yourSecretKey";
    private static String mSecretKey = "vanbau@190";

    public static String encodeString(String plainText){
        String rEncryptedString = "";
        try {
            byte[] mKey = mSecretKey.getBytes("UTF8");
            DESKeySpec keySpec = new DESKeySpec(mKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
            cipher.init(Cipher.ENCRYPT_MODE, key);

            BASE64Encoder base64encoder = new BASE64Encoder();
            rEncryptedString = base64encoder.encode(cipher.doFinal(plainText.getBytes("UTF8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return rEncryptedString;
    }

    public static  String decodeString(String encryptedString){
        String rDecryptedString = "";
        try {
            byte[] mKey = mSecretKey.getBytes("UTF8");
            DESKeySpec keySpec = new DESKeySpec(mKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
            cipher.init(Cipher.DECRYPT_MODE, key);

            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] plainTextPwdBytes = cipher.doFinal(base64decoder.decodeBuffer(encryptedString));
            rDecryptedString = new String(plainTextPwdBytes);
        } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException | IllegalBlockSizeException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }

        return rDecryptedString;
    }
}
