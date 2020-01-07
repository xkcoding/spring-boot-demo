package com.xkcoding.oauth.service;

import com.xkcoding.oauth.entity.SysClientDetails;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.List;

/**
 * 声明自己的实现.
 * 参见 {@link ClientRegistrationService}
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午1:39
 */
public interface SysClientDetailsService extends ClientDetailsService {

    /**
     * 通过客户端 id 查询
     *
     * @param clientId 客户端 id
     * @return 结果
     */
    SysClientDetails findByClientId(String clientId);

    /**
     * 添加客户端信息.
     *
     * @param clientDetails 客户端信息
     * @throws ClientAlreadyExistsException 客户端已存在
     */
    void addClientDetails(SysClientDetails clientDetails) throws ClientAlreadyExistsException;

    /**
     * 更新客户端信息，不包括 clientSecret.
     *
     * @param clientDetails 客户端信息
     * @throws NoSuchClientException 找不到客户端异常
     */
    void updateClientDetails(SysClientDetails clientDetails) throws NoSuchClientException;

    /**
     * 更新客户端密钥.
     *
     * @param clientId     客户端 id
     * @param clientSecret 客户端密钥
     * @throws NoSuchClientException 找不到客户端异常
     */
    void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException;

    /**
     * 删除客户端信息.
     *
     * @param clientId 客户端 id
     * @throws NoSuchClientException 找不到客户端异常
     */
    void removeClientDetails(String clientId) throws NoSuchClientException;

    /**
     * 查询所有
     *
     * @return 结果
     */
    List<SysClientDetails> findAll();
}
