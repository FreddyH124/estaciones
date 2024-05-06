package com.arso.pasarela.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class AppConfig {

    //Como la API de .NET es https pues esto sirve para que un cliente HTTP ignore las verificaciones SSL
    @Bean
    public CloseableHttpClient httpClient() throws Exception {
        return HttpClients.custom()
                .setSSLContext(SSLContextBuilder.create()
                        .loadTrustMaterial(null, (certificate, authType) -> true)
                        .build())
                .build();
    }

    @Bean
    public SimpleHostRoutingFilter simpleHostRoutingFilter(ProxyRequestHelper helper, ZuulProperties zuulProperties, CloseableHttpClient httpClient) {
        return new SimpleHostRoutingFilter(helper, zuulProperties, httpClient);
    }
}
