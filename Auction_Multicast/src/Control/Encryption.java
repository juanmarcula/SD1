/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Encryption
{  
    PublicKey publicKey;
    PrivateKey privateKey;
    
    public Encryption()
    {
        generateKey();
    }
    
    private void generateKey()
    {        
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException ex) {Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);}
        
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();

        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }
    
    public PublicKey getPublicKey()
    {
        return publicKey;
    }
    
    public byte[] encryptMsg(byte[] msg) {
        Cipher cipher;
        byte[] cipherText = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            cipherText = cipher.doFinal(msg);
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipherText;
    }

    public byte[] decryptMsg(Key priv, byte[] cipherText) {

        byte[] msg = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priv);
            msg = cipher.doFinal(cipherText);
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return msg;
    }
    
}
