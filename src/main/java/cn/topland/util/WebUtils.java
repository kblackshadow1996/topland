package cn.topland.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * web get/post工具
 */
public final class WebUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    public static Reply post(String url, MultiValueMap<String, String> parameters) {

        return doPost(new DefaultHttpClient(), url, parameters);
    }

    public static Reply postWithoutSsl(String url, MultiValueMap<String, String> parameters) {

        return doPost(wrapClientWithoutSsl(new DefaultHttpClient()), url, parameters);
    }

    public static String get(String url, String charset) {

        try {

            return doGet(new DefaultHttpClient(), url, charset);
        } catch (IOException e) {

            logger.error("call failed: ", e);
            return null;
        }
    }

    public static String get(String url) {

        try {

            return doGet(new DefaultHttpClient(), url, null);
        } catch (IOException e) {

            logger.error("call failed: ", e);
            return null;
        }
    }

    public static String get(String url, MultiValueMap<String, String> parameters) {

        List<NameValuePair> params = composeParams(parameters);
        try {

            return doGet(new DefaultHttpClient(), new URIBuilder(url).addParameters(params).build().toString(), null);
        } catch (IOException | URISyntaxException e) {

            logger.error("call failed: ", e);
            return null;
        }
    }

    private static String doGet(HttpClient client, String url, String charset) throws IOException {

        HttpGet method = new HttpGet(url);
        long startTime = System.currentTimeMillis();
        HttpResponse response = client.execute(method);
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info(" {}", statusCode);
        logger.info("调用 {} 花费时间(单位：毫秒)： {}", url, System.currentTimeMillis() - startTime);
        if (statusCode != 200) {

            logger.error("Call failed:" + response.getStatusLine() + " status code is " + statusCode);
            return null;
        } else {

            return StringUtils.isBlank(charset)
                    ? EntityUtils.toString(response.getEntity())
                    : EntityUtils.toString(response.getEntity(), charset);
        }
    }

    private static Reply doPost(HttpClient client, String url, MultiValueMap<String, String> parameters) {

        List<NameValuePair> params = composeParams(parameters);
        HttpPost method = new HttpPost(url);
        method.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        HttpResponse response;
        try {

            response = client.execute(method);
        } catch (IOException e) {

            logger.error("Call failed, url is " + url + ", parameters is " + parameters);
            logger.error("error", e);
            return new Reply(Status.SERVICE_UNAVAILABLE, "");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {

            logger.error("Call failed, url is " + url + ", parameters is " + parameters);
            logger.error("Method failed:" + response.getStatusLine());
        }

        Reply reply;
        try {

            reply = new Reply(Status.fromStatusCode(statusCode), EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            return reply;
        } catch (IOException | ParseException e) {

            logger.error("error", e);
            reply = new Reply(Status.BAD_REQUEST, "");
        } finally {

            method.releaseConnection();
            client.getConnectionManager().shutdown();
        }

        return reply;
    }

    private static HttpClient wrapClientWithoutSsl(DefaultHttpClient base) {

        try {

            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            ClientConnectionManager mgr = new PoolingClientConnectionManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception e) {

            logger.error(e.getLocalizedMessage());
            return new DefaultHttpClient();
        }
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