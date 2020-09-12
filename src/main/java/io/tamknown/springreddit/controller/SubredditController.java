package io.tamknown.springreddit.controller;

import io.tamknown.springreddit.dto.SubredditDTO;
import io.tamknown.springreddit.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @PostMapping
    public void createSubreddit(@RequestBody SubredditDTO subredditDTO) {
        subredditService.createSubreddit(subredditDTO);
    }

    @GetMapping
    public List<SubredditDTO> getAllSubreddits() {
        return subredditService.findAllSubreddits();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSubreddit(@PathVariable Long id) {
        return subredditService.findSubreddit(id);
    }





}
