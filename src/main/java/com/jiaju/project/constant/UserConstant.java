package com.jiaju.project.constant;

/**
 * 用户常量
 *
 * @author jiaju
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 系统用户 id（虚拟用户）
     */
    long SYSTEM_USER_ID = 0;

    //  region 权限

    /**
     * 默认权限
     */
    String DEFAULT_ROLE = "user";

    /**
     * 组管理员权限
     */
    String ADMIN_ROLE = "admin";

    /**
     * 超级管理员权限
     */

    String ROOT_ROLE = "root";

    // endregion
}
