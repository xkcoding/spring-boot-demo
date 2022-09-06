package com.xkcoding.ldap.request;

import lombok.Builder;
import lombok.Data;

/**
 * LoginRequest
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-08-26 1:50
 */
@Data
@Builder
public class LoginRequest {

    private String username;

    private String password;

}
