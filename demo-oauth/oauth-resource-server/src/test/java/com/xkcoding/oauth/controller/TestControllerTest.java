package com.xkcoding.oauth.controller;

import com.xkcoding.oauth.AuthorizationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpMethod.GET;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-09  15:46
 */
public class TestControllerTest extends AuthorizationTest {

    private static final String URL = "http://127.0.0.1:8081";

    @Test
    @DisplayName("ROLE_ADMIN 角色测试")
    void testAdminRoleSucceedAndTestRoleFailedWhenPassed() {
        OAuth2RestTemplate template = oauth2RestTemplate("admin", "123456", Collections.singletonList("READ"));
        ResponseEntity<String> response = template.exchange(URL + "/admin", GET, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ADMIN", response.getBody());
        assertThrows(OAuth2AccessDeniedException.class, () -> template.exchange(URL + "/test", GET, null, String.class));
    }

    @Test
    @DisplayName("ROLE_Test 角色测试")
    void testTestRoleSucceedWhenPassed() {
        OAuth2RestTemplate template = oauth2RestTemplate("test", "123456", Collections.singletonList("READ"));
        ResponseEntity<String> response = template.exchange(URL + "/test", GET, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TEST", response.getBody());
        assertThrows(OAuth2AccessDeniedException.class, () -> template.exchange(URL + "/admin", GET, null, String.class));
    }

    @Test
    @DisplayName("SCOPE_READ 授权域测试")
    void testScopeReadWhenPassed() {
        OAuth2RestTemplate template = oauth2RestTemplate("admin", "123456", Collections.singletonList("READ"));
        ResponseEntity<String> response = template.exchange(URL + "/read", GET, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("READ", response.getBody());
        assertThrows(OAuth2AccessDeniedException.class, () -> template.exchange(URL + "/write", GET, null, String.class));
    }

    @Test
    @DisplayName("SCOPE_WRITE 授权域测试")
    void testScopeWriteWhenPassed() {
        OAuth2RestTemplate template = oauth2RestTemplate("admin", "123456", Collections.singletonList("WRITE"));
        ResponseEntity<String> response = template.exchange(URL + "/write", GET, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("WRITE", response.getBody());
        assertThrows(OAuth2AccessDeniedException.class, () -> template.exchange(URL + "/read", GET, null, String.class));
    }

    @Test
    @DisplayName("SCOPE 测试")
    void testScopeWhenPassed() {
        OAuth2RestTemplate template = oauth2RestTemplate("admin", "123456", Arrays.asList("READ", "WRITE"));
        ResponseEntity<String> writeResponse = template.exchange(URL + "/write", GET, null, String.class);
        assertEquals(HttpStatus.OK, writeResponse.getStatusCode());
        assertEquals("WRITE", writeResponse.getBody());
        ResponseEntity<String> readResponse = template.exchange(URL + "/read", GET, null, String.class);
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());
        assertEquals("READ", readResponse.getBody());
    }
}
