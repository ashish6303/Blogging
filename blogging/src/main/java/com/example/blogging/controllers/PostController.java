package com.example.blogging.controllers;

import com.example.blogging.config.AppConstant;
import com.example.blogging.entities.Post;
import com.example.blogging.payloads.ApiResponse;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.services.FileService;
import com.example.blogging.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
            )
    {
        return ResponseEntity.ok(postService.getAllPost(pageNumber, pageSize, sortBy, sortDir));
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

//    Search By keywords
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<?> searchPosts(
            @PathVariable ("keyword") String keyword
    ){
        List<PostDto> searchPost = postService.searchPosts(keyword);
        return new ResponseEntity<>(searchPost, HttpStatus.OK);
    }

//    Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<?> uploadPostImage(
            @RequestParam ("image") MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

//    Methods to serve images
    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException
    {
        InputStream resources = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_GIF_VALUE);
        StreamUtils.copy(resources, response.getOutputStream());
    }


}
