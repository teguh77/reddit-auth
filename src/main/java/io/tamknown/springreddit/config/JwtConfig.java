package io.tamknown.springreddit.config;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private String tokenExpirationAfterDays;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix + " ";
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(String tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

    //GET AUTHORIZATION
    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
