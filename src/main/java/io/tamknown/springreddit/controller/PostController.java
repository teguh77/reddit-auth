package io.tamknown.springreddit.controller;

import io.tamknown.springreddit.dto.PostRequest;
import io.tamknown.springreddit.dto.PostResponse;
import io.tamknown.springreddit.repository.PostRepository;
import io.tamknown.springreddit.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getPosts();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("by-subreddit/{id}")
    public List<PostResponse> getPostsBySubreddit(@PathVariable Long id) {
        return postService.getAllPostsBySubreddit(id);
    }

    @GetMapping("by-user/{name}")
    public List<PostResponse> getPostsByUsername(@PathVariable String username) {
        return postService.getAllPostsByUsername(username);
    }
}
