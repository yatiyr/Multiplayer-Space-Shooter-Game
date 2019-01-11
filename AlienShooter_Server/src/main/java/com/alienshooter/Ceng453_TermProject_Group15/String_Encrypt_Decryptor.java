package com.alienshooter.Ceng453_TermProject_Group15;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/*
This class is for hiding the password in the database
Passphrase was used for checking the functionality of this class
but it can be ommitted now.
 */
public class String_Encrypt_Decryptor {

    //private String passphrase;

    private String encrypted_passphrase;



    public String_Encrypt_Decryptor(String passphrase2, int determinator) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String key = "CENG453ICIN96123";
        Key newkey = new SecretKeySpec(key.getBytes(),"AES");
        Cipher sifre = Cipher.getInstance("AES");
        if(determinator==0)  // encrypt
        {
            //this.passphrase = passphrase2;
            sifre.init(Cipher.ENCRYPT_MODE, newkey);
            byte[] encrypted = sifre.doFinal(passphrase2.getBytes());
            this.encrypted_passphrase = new String(encrypted);
        }
        else // decrypt
        {
            this.encrypted_passphrase = passphrase2;
            sifre.init(Cipher.DECRYPT_MODE, newkey);
            //byte[] decrypted = passphrase2.getBytes();
            //this.passphrase = new String(sifre.doFinal(decrypted));
        }
    }
    /*public String getPassphrase()
    {
        return this.passphrase;
    }*/

    public String getPassphrase_enc()
    {
        return this.encrypted_passphrase;
    }

}
