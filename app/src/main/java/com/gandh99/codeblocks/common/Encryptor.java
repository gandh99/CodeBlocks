package com.gandh99.codeblocks.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

  /* URL: https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ */

  public static String hash512(String plaintext) {
    String encryptedText = "";
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");

      // Add password bytes to digest
      md.update(plaintext.getBytes());

      // Get the hash's bytes
      byte[] bytes = md.digest();

      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      // Get complete hashed password in hex format
      encryptedText = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return encryptedText;
  }

}
