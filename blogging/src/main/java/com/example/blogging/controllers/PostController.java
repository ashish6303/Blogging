package com.example.blogging.controllers;

import com.example.blogging.entities.Post;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<?> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
            )
    {
        PostDto createPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<?> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> getPostByUser = postService.getPostByUser(userId);
        return new ResponseEntity<>(getPostByUser, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<?> getPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/allPosts")
    public ResponseEntity<?> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPost());
    }


}
