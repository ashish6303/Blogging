package com.example.blogging.controllers;

import com.example.blogging.entities.Post;
import com.example.blogging.payloads.ApiResponse;
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

//    Getting post for specific user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<?> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> getPostByUser = postService.getPostByUser(userId);
        return new ResponseEntity<>(getPostByUser, HttpStatus.OK);
    }

//    Getting post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<?> getPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

//    Getting all post
    @GetMapping("/allPosts")
    public ResponseEntity<?> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPost());
    }

//   Getting post by id
    @GetMapping("/postById/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Integer postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

//    Deleting the posts
    @DeleteMapping("/delete/{postId}")
    public ApiResponse detePostById(@PathVariable Integer postId)
    {
        postService.detetePost(postId);
        return new ApiResponse("Post Deleted Successfully", true);
    }

//    This is for Update the post
    @PutMapping("/update/{postId}/posts")
    public ResponseEntity<?> updataPostById(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

}
