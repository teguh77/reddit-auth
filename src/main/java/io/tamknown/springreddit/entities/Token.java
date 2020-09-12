package io.tamknown.springreddit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.String;
import java.time.Instant;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String qwerty;
    private Instant createdDate;

    public Token() {
    }

    private Token(Builder builder) {
        id = builder.id;
        qwerty = builder.qwerty;
        createdDate = builder.createdDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getQwerty() {
        return qwerty;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public static final class Builder {
        private Long id;
        private String qwerty;
        private Instant createdDate;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder qwerty(String val) {
            qwerty = val;
            return this;
        }

        public Builder createdDate(Instant val) {
            createdDate = val;
            return this;
        }

        public Token build() {
            return new Token(this);
        }
    }
}
