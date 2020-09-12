package io.tamknown.springreddit.service;

import io.tamknown.springreddit.dto.CommentDTO;
import io.tamknown.springreddit.entities.Comment;
import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.User;
import io.tamknown.springreddit.exception.ResourceException;
import io.tamknown.springreddit.mapper.CommentMapper;
import io.tamknown.springreddit.repository.CommentRepository;
import io.tamknown.springreddit.repository.PostRepository;
import io.tamknown.springreddit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          CommentMapper commentMapper,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    public void createComment(CommentDTO commentDTO) {
        Post optionalPost = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post comment not found"));

        commentRepository.save(commentMapper.mapToComment(commentDTO, optionalPost, null));
    }

    public List<CommentDTO> getAllCommentForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post comment not found"));

        List<Comment> comments = commentRepository.findByPost(post)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "comment not found"));

        return comments.stream()
                .map(commentMapper::mapToCommentDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getAllCommentForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post comment not found"));

        List<Comment> comments = commentRepository.findByUser(user)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "comment not found"));

        return comments.stream()
                .map(commentMapper::mapToCommentDTO)
                .collect(Collectors.toList());
    }




}
