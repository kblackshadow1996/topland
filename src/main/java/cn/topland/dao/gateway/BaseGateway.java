package cn.topland.dao.gateway;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class BaseGateway {

    protected MultiValueMap<String, String> tokenParam(String accessToken) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        return params;
    }
}