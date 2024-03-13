package com.example.blogging.services.ServiceImpl;

import com.example.blogging.entities.Category;
import com.example.blogging.entities.Post;
import com.example.blogging.entities.User;
import com.example.blogging.exceptions.ResourseNotFoundException;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.repository.CategoryRepo;
import com.example.blogging.repository.PostRepo;
import com.example.blogging.repository.UserRepo;
import com.example.blogging.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "User Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category", "Category Id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImage("default.png");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post createdPost =  postRepo.save(post);
        return modelMapper.map(post, PostDto.class);

    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public Post detetePost(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> allPosts = postRepo.findAll();
        List<PostDto> postDtos = allPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class)) // Corrected mapping test push
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category", "CategoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class)) // Corrected mapping
                .collect(Collectors.toList());
        return postDtos;
    }


    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "UserId", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        System.out.println(postDtos);
        return postDtos;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return null;
    }
}
