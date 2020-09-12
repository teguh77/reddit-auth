package io.tamknown.springreddit.repository;


import io.tamknown.springreddit.entities.Comment;
import io.tamknown.springreddit.entities.Post;
import io.tamknown.springreddit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPost(Post post);

    Optional<List<Comment>> findByUser(User user);
}

