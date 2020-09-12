package io.tamknown.springreddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import io.tamknown.springreddit.dto.PostRequest;
import io.tamknown.springreddit.dto.PostResponse;
import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.Subreddit;
import io.tamknown.springreddit.entities.User;
import io.tamknown.springreddit.exception.ResourceException;
import io.tamknown.springreddit.repository.CommentRepository;
import io.tamknown.springreddit.repository.VoteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

    private CommentRepository commentRepository;
    private VoteRepository voteRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "description", source = "postRequest.description")
    public abstract Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", constant = "0")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", ignore = true)
    @Mapping(target = "downVote", ignore = true)
//    @Mapping(target = "duration", expression = "java(getDuration(post))")
//    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
//    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToPostResponse(Post post);

    Integer commentCount(Post post) {
        return commentRepository
                .findByPost(post)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post comment not found"))
                .size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

//    boolean isPostUpVoted(Post post) {
//        return checkVoteType(post, UPVOTE);
//    }
//
//    boolean isPostDownVoted(Post post) {
//        return checkVoteType(post, DOWNVOTE);
//    }
//
//    private boolean checkVoteType(Post post, VoteType voteType) {
//        if (authService.isLoggedIn()) {
//            Optional<Vote> voteForPostByUser =
//                    voteRepository.findTopByPostAndUser(post,
//                            authService.getCurrentUser());
//            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
//                    .isPresent();
//        }
//        return false;
//    }
}