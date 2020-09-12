package io.tamknown.springreddit.mapper;

import io.tamknown.springreddit.dto.SubredditDTO;
import io.tamknown.springreddit.entities.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subreddit mapSubredditDTOToSubreddit(SubredditDTO subredditDTO);

    @Mapping(target = "numberOfPosts", expression = "java(subreddit.getPosts() == null ? 0 : subreddit.getPosts().size())")
    SubredditDTO mapSubredditToSubredditDTO(Subreddit subreddit);

}
