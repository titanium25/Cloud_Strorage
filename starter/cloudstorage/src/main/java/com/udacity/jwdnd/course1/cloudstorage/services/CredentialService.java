package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential){
        encryptCredential(credential);
        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllByUserId(Integer userId) {
        return credentialMapper.getAllByUserId(userId);
    }

    public Credential getByCredentialId(Integer credentialId) {
        return credentialMapper.getByCredentialId(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteByCredentialId(credentialId);
    }

    public int updateCredential(Credential credential) {
        encryptCredential(credential);
        return credentialMapper.updateCredential(credential);
    }

    private void encryptCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
    }
}
