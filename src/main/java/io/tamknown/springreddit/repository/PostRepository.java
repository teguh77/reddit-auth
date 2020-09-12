package io.tamknown.springreddit.repository;


import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.Subreddit;
import io.tamknown.springreddit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findBySubreddit(Subreddit subreddit);

    Optional<List<Post>> findByUser(User user);

}
