package cn.topland.gateway;

import cn.topland.config.WeworkConfig;
import cn.topland.gateway.response.*;
import cn.topland.util.JsonUtils;
import cn.topland.util.WebUtils;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 企业微信api
 */
@Slf4j
@Component
public class WeworkGateway {

    private static final String CONTACT_USER_URI = "/user";

    private static final String CONTACT_DEPARTMENT_URI = "/department";

    @Value("${wework.api-url}")
    private String WEWORK_URL;

    @Autowired
    private WeworkConfig wework;

    private static String ACCESS_TOKEN = "access_token";

    private LoadingCache<String, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(2L, TimeUnit.HOURS)
            .build(new CacheLoader<>() {
                @Override
                public @Nullable String load(String key) {
                    return null;
                }
            });

    private String loadToken() {

        if (cache.get(ACCESS_TOKEN) == null) {

            AccessToken refreshToken = getToken();
            cache.put(ACCESS_TOKEN, refreshToken.getAccessToken());
        }
        return cache.get(ACCESS_TOKEN);
    }

    private String refreshToken() {

        cache.put(ACCESS_TOKEN, null);
        return loadToken();
    }

    /**
     * 获取access_token
     */
    private AccessToken getToken() {

        String accessTokenUrl = WEWORK_URL + "/gettoken";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("corpid", wework.getCropId());
        params.add("corpsecret", wework.getAgentSecret());
        String accessToken = WebUtils.get(accessTokenUrl, params);
        log.info("wework accessToken: " + accessToken);
        return StringUtils.isNotBlank(accessToken) && JsonUtils.parse(accessToken, AccessToken.class) != null
                ? JsonUtils.parse(accessToken, AccessToken.class)
                : new AccessToken();
    }

    /**
     * 获取用户信息
     */
    public UserInfo getUserInfo(String code) {

        String url = WEWORK_URL + CONTACT_USER_URI + "/getuserinfo";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", loadToken());
        params.add("code", code);
        String userInfo = safeGet(url, params);
        log.info("wework userInfo: " + userInfo);
        return JsonUtils.parse(userInfo, UserInfo.class);
    }

    /**
     * 获取完整用户信息
     */
    public WeworkUser getUser(String userId) {

        String url = WEWORK_URL + CONTACT_USER_URI + "/get";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", loadToken());
        params.add("userid", userId);
        String user = safeGet(url, params);
        return JsonUtils.parse(user, WeworkUser.class);
    }

    /**
     * 获取所有用户
     */
    public List<WeworkUser> listUsers(String deptId, boolean fetchChildren) {

        String url = WEWORK_URL + CONTACT_USER_URI + "/list";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", loadToken());
        params.add("department_id", deptId);
        params.add("fetch_child", fetchChildren ? "1" : "0"); // 是否递归获取子部门成员
        String rawUsers = safeGet(url, params);
        Users users = JsonUtils.parse(rawUsers, Users.class);
        return users == null
                ? new ArrayList<>()
                : users.getWeworkUsers();
    }

    /**
     * 获取所有用户(简略信息)
     */
    public List<WeworkUser> simpleListUsers(String deptId, boolean fetchChildren) {

        String url = WEWORK_URL + CONTACT_USER_URI + "/simplelist";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", loadToken());
        params.add("department_id", deptId);
        params.add("fetch_child", fetchChildren ? "1" : "0"); // 是否递归获取子部门成员
        String rawUsers = safeGet(url, params);
        Users users = JsonUtils.parse(rawUsers, Users.class);
        return users == null
                ? new ArrayList<>()
                : users.getWeworkUsers();
    }

    /**
     * 获取部门，不填写部门默认获取全部
     */
    public List<WeworkDepartment> listDepartments(String deptId) {

        String url = WEWORK_URL + CONTACT_DEPARTMENT_URI + "/list";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", loadToken());
        if (StringUtils.isNotBlank(deptId)) { // 不填则默认查询全量组织架构

            params.add("id", deptId);
        }
        String rawDepartments = safeGet(url, params);
        Departments departments = JsonUtils.parse(rawDepartments, Departments.class);
        return departments == null
                ? new ArrayList<>()
                : departments.getWeworkDepartments();
    }

    /**
     * token可能失效或者过期，所以失败后刷新token再请求一次
     */
    private String safeGet(String url, MultiValueMap<String, String> params) {

        String response = WebUtils.get(url, params);
        if (StringUtils.isBlank(response) || !Objects.equals(JsonUtils.parse(response).get("errmsg"), "ok")) {

            params.put("access_token", List.of(refreshToken()));
            response = WebUtils.get(url, params);
        }
        return response;
    }
}