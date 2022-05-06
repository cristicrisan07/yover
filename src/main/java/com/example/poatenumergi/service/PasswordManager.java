package com.example.poatenumergi.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerOperationsService.class);
    /**
     *
     * @param myKey String to be process in order to retrieve a key.
     */
    private static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.info("Invalid algorithm");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param strToEncrypt String value to be encrypted.
     * @param secret String value that represents the key used in encoding.
     * @return the encrypted value.
     */
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            LOGGER.info("Encryption has been successfully done.");
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     *
     * @param strToDecrypt String value to be decrypted.
     * @param secret String value that represents the key used in decoding.
     * @return  the decrypted value.
     */
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            LOGGER.info("Decryption has been successfully performed.");
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

