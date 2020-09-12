package io.tamknown.springreddit.service;

import io.tamknown.springreddit.dto.SubredditDTO;
import io.tamknown.springreddit.entities.Subreddit;
import io.tamknown.springreddit.mapper.SubredditMapper;
import io.tamknown.springreddit.repository.SubredditRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    private final SubredditMapper subredditMapper;
    private final SubredditRepository subredditRepository;

    public SubredditService(SubredditMapper subredditMapper,
                            SubredditRepository subredditRepository) {
        this.subredditMapper = subredditMapper;
        this.subredditRepository = subredditRepository;
    }

    public void createSubreddit(SubredditDTO subredditDTO) {
        subredditRepository
                .save(subredditMapper.mapSubredditDTOToSubreddit(subredditDTO));
    }

    public List<SubredditDTO> findAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToSubredditDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> findSubreddit(Long id) {
        Optional<Subreddit> subreddit = subredditRepository.findById(id);
        if(subreddit.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(subredditMapper.mapSubredditToSubredditDTO(subreddit.get()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Subreddit Not Found");
        }
    }
}
