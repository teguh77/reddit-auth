package io.tamknown.springreddit.service;

import io.tamknown.springreddit.dto.PostRequest;
import io.tamknown.springreddit.dto.PostResponse;
import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.Subreddit;
import io.tamknown.springreddit.entities.User;
import io.tamknown.springreddit.exception.ResourceException;
import io.tamknown.springreddit.mapper.PostMapper;
import io.tamknown.springreddit.repository.PostRepository;
import io.tamknown.springreddit.repository.SubredditRepository;
import io.tamknown.springreddit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(SubredditRepository subredditRepository,
                       PostMapper postMapper,
                       PostRepository postRepository,
                       UserRepository userRepository) {
        this.subredditRepository = subredditRepository;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void createPost(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Cannot create post"));
        postRepository.save(postMapper.mapToPost(postRequest, subreddit, null));
    }

    public ResponseEntity<?> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postMapper.mapToPostResponse(optionalPost.get()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Post not found");
        }
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository
                .findById(subredditId)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post not found"));
        List<Post> posts = postRepository
                .findBySubreddit(subreddit)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post not found"));
        return posts
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostsByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post not found"));
        List<Post> posts = postRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "Post not found"));
        return posts.stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }




}
