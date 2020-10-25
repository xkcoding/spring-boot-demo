package com.xkcoding.oauth.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xkcoding.oauth.oauth.AuthorizationServerInfo.getUrl;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 授权码模式测试.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-06 20:43
 */
public class AuthorizationCodeGrantTests {

    private AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
    private AuthorizationServerInfo authorizationServerInfo = new AuthorizationServerInfo();

    @BeforeEach
    void setUp() {
        resource.setAccessTokenUri(getUrl("/oauth/token"));
        resource.setClientId("oauth2");
        resource.setId("oauth2");
        resource.setScope(Arrays.asList("READ", "WRITE"));
        resource.setAccessTokenUri(getUrl("/oauth/token"));
        resource.setUserAuthorizationUri(getUrl("/oauth/authorize"));
    }

    @Test
    void testCannotConnectWithoutToken() {
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
        assertThrows(UserRedirectRequiredException.class,
            () -> template.getForObject(getUrl("/oauth/me"), String.class));
    }

    @Test
    void testAttemptedTokenAcquisitionWithNoRedirect() {
        AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
        assertThrows(UserRedirectRequiredException.class,
            () -> provider.obtainAccessToken(resource, new DefaultAccessTokenRequest()));
    }

    /**
     * 这里不使用他提供的是因为很多地方不符合我们的需要
     * 比如 csrf，比如许多有些是自己自定义的端点这些
     * 所以只有我们一步一步的来进行测试拿到授权码
     */
    @Test
    void testCodeAcquisitionWithCorrectContext() {
        // 1. 请求登录页面获取 _csrf 的 value 以及 cookie
        ResponseEntity<String> page = authorizationServerInfo.getForString("/oauth/login");
        assertNotNull(page.getBody());
        String cookie = page.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(page.getBody());
        assertTrue(matcher.find());

        // 2. 添加表单数据
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "admin");
        form.add("password", "123456");
        form.add("_csrf", matcher.group(1));

        // 3. 登录授权并获取登录成功的 cookie
        ResponseEntity<Void> response = authorizationServerInfo
            .postForStatus("/authorization/form", headers, form);
        assertNotNull(response);
        cookie = response.getHeaders().getFirst("Set-Cookie");
        headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.setAccept(Collections.singletonList(MediaType.ALL));

        // 4. 请求到 确认授权页面 ，获取确认授权页面的 _csrf 的 value
        ResponseEntity<String> confirm = authorizationServerInfo
            .getForString("/oauth/authorize?response_type=code&client_id=oauth2&redirect_uri=http://example.com&scope=READ", headers);

        headers = confirm.getHeaders();
        // 确认过一次后，后面都会自动确认了，这里判断下是不是重定向请求
        // 如果不是，就表示是第一次，需要确认授权
        if (!confirm.getStatusCode().is3xxRedirection()) {
            assertNotNull(confirm.getBody());
            Matcher matcherConfirm = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(confirm.getBody());
            assertTrue(matcherConfirm.find());
            headers = new HttpHeaders();
            headers.set("Cookie", cookie);
            headers.setAccept(Collections.singletonList(MediaType.ALL));

            // 5. 构建 同意授权 的表单
            form = new LinkedMultiValueMap<>();
            form.add("user_oauth_approval", "true");
            form.add("scope.READ", "true");
            form.add("_csrf", matcherConfirm.group(1));

            // 6. 请求授权，获取 授权码
            headers = authorizationServerInfo.postForHeaders("/oauth/authorize", form, headers);
        }

        URI location = headers.getLocation();
        assertNotNull(location);
        String query = location.getQuery();
        assertNotNull(query);
        String[] result = query.split("=");
        assertEquals(2, result.length);
        System.out.println(result[1]);
    }

}
