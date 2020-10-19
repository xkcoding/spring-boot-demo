package com.xkcoding.oauth.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;

import static com.xkcoding.oauth.oauth.AuthorizationServerInfo.getUrl;
import static org.junit.jupiter.api.Assertions.*;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午9:14
 */
public class ResourceOwnerPasswordGrantTests {

    @Test
    void testConnectDirectlyToResourceServer() {
        assertNotNull(accessToken());
    }

    public static String accessToken() {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(getUrl("/oauth/token"));
        resource.setClientId("oauth2");
        resource.setClientSecret("oauth2");
        resource.setId("oauth2");
        resource.setScope(Arrays.asList("READ", "WRITE"));
        resource.setUsername("admin");
        resource.setPassword("123456");
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
        OAuth2AccessToken accessToken = template.getAccessToken();
        return accessToken.getValue();
    }
}
