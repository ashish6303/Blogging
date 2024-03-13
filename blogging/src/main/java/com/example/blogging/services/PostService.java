package com.example.blogging.services;

import com.example.blogging.entities.Post;
import com.example.blogging.payloads.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void detetePost(Integer postId);

    List<PostDto> getAllPost();

    PostDto getPostById(Integer postId);


    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<Post> searchPosts(String keyword);

}

