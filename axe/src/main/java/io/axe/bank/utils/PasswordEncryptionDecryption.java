package io.axe.bank.utils;

import io.axe.bank.exceptions.BankPassError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class PasswordEncryptionDecryption {
    public static final String ENCRYPTING_ERROR = "Error while encrypting!";
    public static final String DECRYPTING_ERROR = "Error while decrypting!";
    public static final int LENGTH = 16;
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B";
    private static SecretKeySpec secretKeySpec;

    private static void prepareSecreteKey() {
        MessageDigest sha;
        try {
            byte[] key = PasswordEncryptionDecryption.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, LENGTH);
            secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encryptPassword(String passwordToEncrypt) {
        try {
            prepareSecreteKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(passwordToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new BankPassError(ENCRYPTING_ERROR);
        }
    }

    public String decryptPassword(String passwordToDecrypt) {
        try {
            prepareSecreteKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(passwordToDecrypt)));
        } catch (Exception e) {
            throw new BankPassError(DECRYPTING_ERROR);
        }
    }
}
