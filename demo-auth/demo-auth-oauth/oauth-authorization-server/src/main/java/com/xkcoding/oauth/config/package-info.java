/**
 * spring security oauth2 的相关配置。
 * 使用 spring boot oauth2 自动配置。
 * {@link com.xkcoding.oauth.config.Oauth2AuthorizationServerConfig}
 * 授权服务器相关的配置，主要设置授权服务器如何读取客户端、用户信息和一些端点配置
 * 可以在这里配置更多的东西，例如端点映射，token 增强等
 * <p>
 * {@link com.xkcoding.oauth.config.Oauth2AuthorizationTokenConfig}
 * 授权服务器 token 相关的配置，主要设置 jwt、加密方式等信息
 * <p>
 * {@link com.xkcoding.oauth.config.ClientLogoutSuccessHandler}
 * 资源服务器退出以后的处理。在授权码模式中，所有的客户端都需要跳转到授权服务器进行登录
 * 当登录成功以后跳转到回调地址，如果用户需要登出，也要跳转到授权服务器这里进行登出
 * 但是 spring security oauth2 似乎并没有这个逻辑。
 * 所以自己给登出端点加了一个 redirect_url 参数，表示登出成功以后要跳转的地址
 * 这个处理器就是来完成登出成功以后的跳转操作的。
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-07 9:16
 */
package com.xkcoding.oauth.config;
