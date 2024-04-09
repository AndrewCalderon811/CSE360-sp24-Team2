package spr24cse360;

import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	
	public byte[] encrypt(String plaintext) throws Exception {
        String key = "mAstRcArtErRocKs";
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipherE = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipherE.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipherE.doFinal(padString(plaintext).getBytes());
    }

    public String decrypt(byte[] cipher) throws Exception {
    	if (cipher == null) {
    		return "";
    	}
        String key = "mAstRcArtErRocKs";
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipherD = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipherD.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipherD.doFinal(cipher);
        return removePadding(new String(decryptedBytes));
    }

    private String padString(String input) {
        int paddingLength = 16 - input.length() % 16;
        StringBuilder paddedInput = new StringBuilder(input);
        for (int i = 0; i < paddingLength; i++) {
            paddedInput.append((char) paddingLength);
        }
        return paddedInput.toString();
    }

    private String removePadding(String input) {
        int paddingLength = input.charAt(input.length() - 1);
        return input.substring(0, input.length() - paddingLength);
    }
	
	
}
