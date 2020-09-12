package io.tamknown.springreddit.entities;

import com.sun.istack.NotNull;
import io.tamknown.springreddit.dto.VoteType;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Long getVoteId() {
        return voteId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }
}
