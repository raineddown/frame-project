package com.example.demo.contants;


public class Constant {
    /**
     * Contants 加入 用户名 key 常量
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 角色信息key
     */
    public static final String ROLES_INFOS_KEY="roles-infos-key";

    /**
     * 权限信息key
     */
    public static final String PERMISSIONS_INFOS_KEY="permissions-infos-key";

    /**
     *  业务访问token
     */
    public static final String ACCESS_TOKEN="authorization";


    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key_";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY="account-lock-key_";
    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY="deleted-user-key_";

    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY="shiro-cache:radarSoftware.cn.shiro.CustomRealm.authorizationCache:";

    /**
     * 判断是否达发送上限的key
     */
    public final static String REGISTER_CODE_COUNT_KEY="register-code-count-key_";
    /**
     * 验证码有效期key
     */
    public final static String REGISTER_CODE_COUNT_VALIDITY_KEY="register-code-count-validity-key_";


    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST="jwt-refresh-token-blacklist_";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist_";

    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN="refreshToken";


    /**
     * 标记新的refresh_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION="jwt-refresh-identification_";

    /**
     * 部门编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key_";


}
