package io.tamknown.springreddit.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Subreddit() {
    }

    private Subreddit(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
        posts = builder.posts;
        createdDate = builder.createdDate;
        user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    public static final class Builder {
        private Long id;
        private @NotBlank(message = "Community name is required") String name;
        private @NotBlank(message = "Description is required") String description;
        private List<Post> posts;
        private Instant createdDate;
        private User user;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(@NotBlank(message = "Community name is required") String val) {
            name = val;
            return this;
        }

        public Builder description(@NotBlank(message = "Description is required") String val) {
            description = val;
            return this;
        }

        public Builder posts(List<Post> val) {
            posts = val;
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

        public Subreddit build() {
            return new Subreddit(this);
        }
    }
}
