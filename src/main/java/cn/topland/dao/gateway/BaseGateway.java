package cn.topland.dao.gateway;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.format.DateTimeFormatter;

public abstract class BaseGateway {

    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    protected MultiValueMap<String, String> tokenParam(String accessToken) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        return params;
    }
}