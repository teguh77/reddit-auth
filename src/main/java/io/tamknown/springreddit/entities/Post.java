package io.tamknown.springreddit.entities;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotBlank(message = "Post name cannot be empty or null")
    private String postName;
    @Nullable
    private String url;
    @Lob
    @Nullable
    private String description;
    private Integer voteCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;

    public Post() {
    }

    private Post(Builder builder) {
        postId = builder.postId;
        postName = builder.postName;
        url = builder.url;
        description = builder.description;
        voteCount = builder.voteCount;
        user = builder.user;
        createdDate = builder.createdDate;
        subreddit = builder.subreddit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostName() {
        return postName;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }


    public static final class Builder {
        private Long postId;
        private @NotBlank(message = "Post name cannot be empty or null") String postName;
        private String url;
        private String description;
        private Integer voteCount;
        private User user;
        private Instant createdDate;
        private Subreddit subreddit;

        private Builder() {
        }

        public Builder postId(Long val) {
            postId = val;
            return this;
        }

        public Builder postName(@NotBlank(message = "Post name cannot be empty or null") String val) {
            postName = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder voteCount(Integer val) {
            voteCount = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Builder createdDate(Instant val) {
            createdDate = val;
            return this;
        }

        public Builder subreddit(Subreddit val) {
            subreddit = val;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
