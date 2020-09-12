package io.tamknown.springreddit.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Comment() {
    }

    private Comment(Builder builder) {
        id = builder.id;
        text = builder.text;
        post = builder.post;
        createdDate = builder.createdDate;
        user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Post getPost() {
        return post;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }


    public static final class Builder {
        private Long id;
        private @NotEmpty String text;
        private Post post;
        private Instant createdDate;
        private User user;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder text(@NotEmpty String val) {
            text = val;
            return this;
        }

        public Builder post(Post val) {
            post = val;
            return this;
        }

        public Builder createdDate(Instant val) {
            createdDate = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
