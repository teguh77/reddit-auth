package io.tamknown.springreddit.dto;

import javax.validation.constraints.NotBlank;

public class RefreshRequest {

    @NotBlank
    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
