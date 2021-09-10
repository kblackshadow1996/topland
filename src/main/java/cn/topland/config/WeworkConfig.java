package cn.topland.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 企业微信基础配置,包含企业id,应用id等
 */
@Data
@Component
public final class WeworkConfig {

    @Value("${wework.crop-id}")
    private String cropId;

    @Value("${wework.agent-id}")
    private String agentId;

    @Value("${wework.agent-secret}")
    private String agentSecret;

    @Value("${wework.redirect-url}")
    private String redirectUrl;
}