package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential){
        // encrypt password when user adds new credential before store to DB:
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // set new encoded key and encrypted password to Credential Class Model:
        credential.setKey(encodedKey);

        // set encrypted password to Credential Class Model:
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);

        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllByUserId(Integer userId) {
        return credentialMapper.getAllByUserId(userId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteByCredentialId(credentialId);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.updateCredential(credential);
    }
}
