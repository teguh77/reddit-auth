package io.tamknown.springreddit.dto;


import io.tamknown.springreddit.exception.ResourceException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST,"Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
