package com.xkcoding.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/9 下午3:44
 */
public class AuthorizationTest {
    public static final String AUTHORIZATION_SERVER = "http://193.112.94.161:8080";

    protected OAuth2RestTemplate oauth2RestTemplate(String username, String password, List<String> scope) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(AUTHORIZATION_SERVER + "/oauth/token");
        resource.setClientId("oauth2");
        resource.setClientSecret("oauth2");
        resource.setId("oauth2");
        resource.setScope(scope);
        resource.setUsername(username);
        resource.setPassword(password);
        return new OAuth2RestTemplate(resource);
    }

    @Test
    void testAccessTokenWhenPassed() {
        assertNotNull(oauth2RestTemplate("admin", "123456", Collections.singletonList("READ"))
            .getAccessToken());
    }
}
