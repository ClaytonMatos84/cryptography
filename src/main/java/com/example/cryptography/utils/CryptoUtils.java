package com.example.cryptography.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class CryptoUtils {

    private void generateKeys() throws Exception {
        if (Files.exists(Path.of("public.key")) && Files.exists(Path.of("private.key"))) return;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        try (FileOutputStream publicKeyFile = new FileOutputStream("public.key");
             FileOutputStream privateKeyFile = new FileOutputStream("private.key")) {

            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            publicKeyFile.write(publicKeyBytes);

            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            privateKeyFile.write(privateKeyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String value) {
        try {
            generateKeys();

            File publicKeyFile = new File("public.key");
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedMessage = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public String decrypt(String value) {
        try {
            generateKeys();

            File privateKeyFile = new File("private.key");
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
