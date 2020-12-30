package com.xkcoding.oauth.oauth;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * 授权服务器工具类.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午8:44
 */
@SuppressWarnings("all")
public class AuthorizationServerInfo {
    public static final String HOST = "http://193.112.94.161:8080";

    private RestTemplate client;

    public AuthorizationServerInfo() {
        client = new RestTemplate();
        client.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                connection.setInstanceFollowRedirects(false);
            }
        });
        client.setErrorHandler(new ResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    public ResponseEntity<String> getForString(String path, final HttpHeaders headers) {
        return client.exchange(getUrl(path), HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
    }

    public ResponseEntity<String> getForString(String path) {
        return getForString(path, new HttpHeaders());
    }

    public ResponseEntity<Void> postForStatus(String path, HttpHeaders headers, MultiValueMap<String, String> formData) {
        HttpHeaders actualHeaders = new HttpHeaders();
        actualHeaders.putAll(headers);
        actualHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return client.exchange(getUrl(path), HttpMethod.POST,
            new HttpEntity<>(formData, actualHeaders), (Class<Void>) null);
    }


    public static String getUrl(String path) {
        return HOST + path;
    }

    public HttpHeaders postForHeaders(String path, MultiValueMap<String, String> formData, final HttpHeaders headers) {
        RequestCallback requestCallback = new NullRequestCallback();
        if (headers != null) {
            requestCallback = request -> request.getHeaders().putAll(headers);
        }
        StringBuilder builder = new StringBuilder(getUrl(path));
        if (!path.contains("?")) {
            builder.append("?");
        } else {
            builder.append("&");
        }
        for (String key : formData.keySet()) {
            for (String value : formData.get(key)) {
                builder.append(key).append("=").append(value);
                builder.append("&");
            }
        }
        builder.deleteCharAt(builder.length() - 1);

        return client.execute(builder.toString(), HttpMethod.POST, requestCallback,
            HttpMessage::getHeaders);
    }

    private static final class NullRequestCallback implements RequestCallback {
        public void doWithRequest(ClientHttpRequest request) {
        }
    }
}
