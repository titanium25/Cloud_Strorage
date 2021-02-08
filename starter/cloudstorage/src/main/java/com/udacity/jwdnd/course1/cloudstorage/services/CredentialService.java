package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(Credential credential){
        return credentialMapper.insert(credential);
    }

    public Credential getByCredentialId(Integer credentialId) {
        return credentialMapper.getByCredentialId(credentialId);
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
