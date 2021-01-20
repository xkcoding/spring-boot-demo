package com.xkcoding.oauth.repostiory;

import com.xkcoding.oauth.entity.SysClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

/**
 * 客户端信息.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-06 13:09
 */
public interface SysClientDetailsRepository extends JpaRepository<SysClientDetails, Long> {

    /**
     * 通过 clientId 查找客户端信息.
     *
     * @param clientId clientId
     * @return 结果
     */
    Optional<SysClientDetails> findFirstByClientId(String clientId);

    /**
     * 根据客户端 id 删除客户端
     *
     * @param clientId 客户端id
     */
    @Modifying
    void deleteByClientId(String clientId);

}
