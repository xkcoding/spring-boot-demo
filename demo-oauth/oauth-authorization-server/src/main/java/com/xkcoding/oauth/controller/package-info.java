/**
 * 控制器。除了业务逻辑的以外,提供两个控制器来帮助完成自定义：
 * {@link com.xkcoding.oauth.controller.AuthorizationController}
 * 自定义的授权控制器，重新设置到我们的界面中去，不使用他的默认实现
 *
 * {@link com.xkcoding.oauth.controller.Oauth2Controller}
 * 页面跳转的控制器，这里拿出来是因为真的可以做很多事。比如登录的时候携带点什么
 * 或者退出的时候携带什么标识，都可以。
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-07 11:25
 * @see org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
 */
package com.xkcoding.oauth.controller;
