package com.example.blogging.services.ServiceImpl;

import com.example.blogging.entities.Category;
import com.example.blogging.entities.Post;
import com.example.blogging.entities.User;
import com.example.blogging.exceptions.ResourseNotFoundException;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.payloads.PostResponse;
import com.example.blogging.repository.CategoryRepo;
import com.example.blogging.repository.PostRepo;
import com.example.blogging.repository.UserRepo;
import com.example.blogging.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    To create new post
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "User Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category", "Category Id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post createdPost =  postRepo.save(post);
        return modelMapper.map(createdPost, PostDto.class);

    }

//    This is for update the post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post posts = postRepo.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post", "PostId", postId));
        posts.setTitle(postDto.getTitle());
        posts.setContent(postDto.getContent());
        posts.setImageName(postDto.getImageName());
        Post updatedPost = postRepo.save(posts);
        return modelMapper.map(updatedPost,PostDto.class);
    }

//    To delete the post by Id
    @Override
    public void detetePost(Integer postId) {
        Post posts = postRepo.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post", "PostId", postId));
        postRepo.delete(posts);
    }

//    To get all the posts It also contains paging and sorting
    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

//        if (sortDir.equalsIgnoreCase("asc"))
//        {
//            sort = Sort.by(sortBy).ascending();
//        }else{
//            sort = Sort.by(sortBy).descending();
//        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page <Post> pagePost = postRepo.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class)) // Corrected mapping
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

//  Getting post by Id
    @Override
    public PostDto getPostById(Integer postId) {
        Post posts = postRepo.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post", "PostId", postId));
        PostDto postDto = modelMapper.map(posts,PostDto.class);
        return postDto;
    }

//    Getting posts by Category
    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category", "CategoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class)) // Corrected mapping
                .collect(Collectors.toList());
        return postDtos;
    }

//  Getting posts by user
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

//   To search the post
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class)) // Change 'posts' to 'post'
                .collect(Collectors.toList());
        return postDtos;
    }

}
