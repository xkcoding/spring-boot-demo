package com.xkcoding.social.qq.connect;

import com.xkcoding.social.qq.api.QQ;
import com.xkcoding.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * <p>
 * QQ 用户适配器
 * </p>
 *
 * @package: com.xkcoding.social.qq.connect
 * @description: QQ 用户适配器
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 13:46
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试api是否可用，默认返回true
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 将自己实现的获取用户信息，转换为spring social返回的用户信息
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProviderUserId(userInfo.getOpenId());
        values.setProfileUrl(null);
    }

    /**
     * 用户主页，QQ默认没有用户主页
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return UserProfile.EMPTY;
    }

    /**
     * 更新用户状态，空实现
     */
    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }
}
