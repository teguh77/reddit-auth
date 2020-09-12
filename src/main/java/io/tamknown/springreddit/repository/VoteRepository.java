package io.tamknown.springreddit.repository;


import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.User;
import io.tamknown.springreddit.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUser(Post post, User currentUser);
}
