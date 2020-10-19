package com.xkcoding.oauth.service.impl;

import com.xkcoding.oauth.entity.SysClientDetails;
import com.xkcoding.oauth.repostiory.SysClientDetailsRepository;
import com.xkcoding.oauth.service.SysClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端 相关操作.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午1:37
 */
@Service
@RequiredArgsConstructor
public class SysClientDetailsServiceImpl implements SysClientDetailsService {

    private final SysClientDetailsRepository sysClientDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String id) throws ClientRegistrationException {
        return sysClientDetailsRepository.findFirstByClientId(id)
            .orElseThrow(() -> new ClientRegistrationException("Loading client exception."));
    }

    @Override
    public SysClientDetails findByClientId(String clientId) {
        return sysClientDetailsRepository.findFirstByClientId(clientId)
            .orElseThrow(() -> new ClientRegistrationException("Loading client exception."));
    }

    @Override
    public void addClientDetails(SysClientDetails clientDetails) throws ClientAlreadyExistsException {
        clientDetails.setId(null);
        if (sysClientDetailsRepository.findFirstByClientId(clientDetails.getClientId()).isPresent()) {
            throw new ClientAlreadyExistsException(String.format("Client id %s already exist.", clientDetails.getClientId()));
        }
        sysClientDetailsRepository.save(clientDetails);
    }

    @Override
    public void updateClientDetails(SysClientDetails clientDetails) throws NoSuchClientException {
        SysClientDetails exist = sysClientDetailsRepository.findFirstByClientId(clientDetails.getClientId())
            .orElseThrow(() -> new NoSuchClientException("No such client!"));
        clientDetails.setClientSecret(exist.getClientSecret());
        sysClientDetailsRepository.save(clientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException {
        SysClientDetails exist = sysClientDetailsRepository.findFirstByClientId(clientId)
            .orElseThrow(() -> new NoSuchClientException("No such client!"));
        exist.setClientSecret(passwordEncoder.encode(clientSecret));
        sysClientDetailsRepository.save(exist);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        sysClientDetailsRepository.deleteByClientId(clientId);
    }

    @Override
    public List<SysClientDetails> findAll() {
        return sysClientDetailsRepository.findAll();
    }

}
