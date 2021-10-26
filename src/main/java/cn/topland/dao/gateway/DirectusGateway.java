package cn.topland.dao.gateway;

import cn.topland.util.HttpDelete;
import cn.topland.util.Reply;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * directus操作类
 */
@Slf4j
@Component
public class DirectusGateway {

    @Value("${directus.url}")
    private String DIRECTUS_URL;

    public Reply patch(String url, MultiValueMap<String, String> parameters, JsonNode body) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPatch httpPatch;
        HttpResponse response;
        try {

            httpPatch = new HttpPatch(new URIBuilder(DIRECTUS_URL + url).addParameters(composeParams(parameters)).build().toString());
            httpPatch.setHeader("Content-Type", "application/json");
            httpPatch.setEntity(new StringEntity(body.toPrettyString(), StandardCharsets.UTF_8));
            response = client.execute(httpPatch);
        } catch (IOException | URISyntaxException e) {

            log.error(e.getMessage());
            return new Reply(Response.Status.SERVICE_UNAVAILABLE, "");
        }
        return reply(client, httpPatch, response);
    }

    public Reply post(String url, MultiValueMap<String, String> parameters, JsonNode body) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost;
        HttpResponse response;
        try {

            httpPost = new HttpPost(new URIBuilder(DIRECTUS_URL + url).addParameters(composeParams(parameters)).build().toString());
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(body.toPrettyString(), StandardCharsets.UTF_8));
            response = client.execute(httpPost);
        } catch (IOException | URISyntaxException e) {

            log.error(e.getMessage());
            return new Reply(Response.Status.SERVICE_UNAVAILABLE, "");
        }
        return reply(client, httpPost, response);
    }

    public Reply delete(String url, MultiValueMap<String, String> parameters, JsonNode body) {

        CloseableHttpClient client = HttpClients.createDefault();
        cn.topland.util.HttpDelete httpDelete;
        HttpResponse response;
        try {

            httpDelete = new HttpDelete(new URIBuilder(DIRECTUS_URL + url).addParameters(composeParams(parameters)).build().toString());
            httpDelete.setHeader("Content-Type", "application/json");
            httpDelete.setEntity(new StringEntity(body.toPrettyString(), StandardCharsets.UTF_8));
            response = client.execute(httpDelete);
        } catch (IOException | URISyntaxException e) {

            log.error(e.getMessage());
            return new Reply(Response.Status.SERVICE_UNAVAILABLE, "");
        }
        return reply(client, httpDelete, response);
    }

    private static Reply reply(CloseableHttpClient client, HttpRequestBase httpRequest, HttpResponse response) {

        int statusCode = response.getStatusLine().getStatusCode();
        Reply reply;
        try {

            reply = new Reply(Response.Status.fromStatusCode(statusCode), EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            return reply;
        } catch (IOException | ParseException e) {

            log.error("error", e);
            reply = new Reply(Response.Status.BAD_REQUEST, "");
        } finally {

            httpRequest.releaseConnection();
            client.getConnectionManager().shutdown();
        }
        return reply;
    }

    private static List<NameValuePair> composeParams(MultiValueMap<String, String> parameters) {

        List<NameValuePair> params = new ArrayList<>();
        Iterator<String> iterator = parameters.keySet().iterator();

        while (iterator.hasNext()) {

            String key = iterator.next();
            Iterator<String> param = parameters.get(key).iterator();

            while (param.hasNext()) {

                String value = param.next();
                params.add(new BasicNameValuePair(key, value));
            }
        }
        return params;
    }
}