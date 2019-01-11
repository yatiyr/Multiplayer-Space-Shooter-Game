package com.alienshooter.Ceng453_TermProject_Group15.service;

import com.alienshooter.Ceng453_TermProject_Group15.model.USER;
import com.alienshooter.Ceng453_TermProject_Group15.repository.USERRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alienshooter.Ceng453_TermProject_Group15.String_Encrypt_Decryptor;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service("USERService")
public class USERService {

    private USERRepository userRepository;

    @Autowired
    public USERService(USERRepository userRepository) {
        this.userRepository = userRepository;
    }

    public USER findByName(String username) {
        return userRepository.findByName(username);

    }

    /* Returns the specified user according to the id given */
    public USER findById(long id){
        return userRepository.getOne(id);
    }

    /* Saves the user.Encrypts the given password before putting it to the database*/
    public void saveUser(USER user) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String_Encrypt_Decryptor encrypt_decryptor = new String_Encrypt_Decryptor(user.getPassword(),0);
        user.setPassword(encrypt_decryptor.getPassphrase_enc());
        userRepository.save(user);
    }

    /*Updates the name of the user*/
    public void username_update(USER user)
    {
        userRepository.username_update(user.getId(),user.getName());
    }

    /*Updates the password of the user.This takes the password as the written string but it encrypts it and then saves it accordingly*/
    public void password_update(USER user) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String_Encrypt_Decryptor encrypt_decryptor = new String_Encrypt_Decryptor(user.getPassword(),0);
        user.setPassword(encrypt_decryptor.getPassphrase_enc());
        userRepository.password_update(user.getId(),user.getPassword());
    }

    /*Deletes the specified user*/
    public void delete_user(USER user)
    {
        userRepository.delete_user(user.getId());
    }


}