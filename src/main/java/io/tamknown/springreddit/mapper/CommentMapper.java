package io.tamknown.springreddit.mapper;

import io.tamknown.springreddit.dto.CommentDTO;
import io.tamknown.springreddit.entities.Comment;
import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    Comment mapToComment(CommentDTO commentDTO, Post post, User user);

    @Mapping(target = "postId", source = "comment.post.postId")
    @Mapping(target = "username", source = "comment.user.username")
    CommentDTO mapToCommentDTO(Comment comment);
}
