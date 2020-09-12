package io.tamknown.springreddit.controller;

import io.tamknown.springreddit.dto.CommentDTO;
import io.tamknown.springreddit.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
    }

    @GetMapping("/by-post/{postId}")
    public List<CommentDTO> getAllCommentForPost(@PathVariable Long postId) {
        return commentService.getAllCommentForPost(postId);
    }

    @GetMapping("/by-post/{username}")
    public List<CommentDTO> getAllCommentForUser(@PathVariable String username) {
        return commentService.getAllCommentForUser(username);
    }

}
