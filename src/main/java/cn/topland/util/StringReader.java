package cn.topland.util;

import org.springframework.stereotype.Component;

/**
 * 读取url对应页面源码
 */
@Component
public class StringReader implements URLReader<String> {

    @Override
    public String read(String url) {

        return directRead(convertToHttp(url));
    }

    private String convertToHttp(String url) {

        if (url.startsWith("http://")) {

            return url;
        } else if (url.startsWith("https://")) {

            return url.replaceFirst("https://", "http://");
        } else {

            return url.replaceFirst("//", "http://");
        }
    }

    private String directRead(String url) {

        return WebUtils.get(url, "utf-8");
    }
}