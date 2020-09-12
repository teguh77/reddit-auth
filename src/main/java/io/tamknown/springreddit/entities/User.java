package io.tamknown.springreddit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private String roles;
    private Instant created;
    private boolean enabled;

    public User() {
    }

    private User(Builder builder) {
        userId = builder.userId;
        username = builder.username;
        password = builder.password;
        email = builder.email;
        roles = builder.roles;
        created = builder.created;
        enabled = builder.enabled;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreated() {
        return created;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getRoles() {
        return roles;
    }

    public static final class Builder {
        private Long userId;
        private @NotBlank(message = "Username is required") String username;
        private @NotBlank(message = "Password is required") String password;
        private @Email @NotEmpty(message = "Email is required") String email;
        private String roles;
        private Instant created;
        private boolean enabled;

        private Builder() {
        }

        public Builder userId(Long val) {
            userId = val;
            return this;
        }

        public Builder username(@NotBlank(message = "Username is required") String val) {
            username = val;
            return this;
        }

        public Builder password(@NotBlank(message = "Password is required") String val) {
            password = val;
            return this;
        }

        public Builder email(@Email @NotEmpty(message = "Email is required") String val) {
            email = val;
            return this;
        }

        public Builder roles(String val) {
            roles = val;
            return this;
        }

        public Builder created(Instant val) {
            created = val;
            return this;
        }

        public Builder enabled(boolean val) {
            enabled = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
