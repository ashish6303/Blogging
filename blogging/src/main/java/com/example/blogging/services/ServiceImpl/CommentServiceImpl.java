package com.example.blogging.services.ServiceImpl;

import com.example.blogging.entities.Comment;
import com.example.blogging.entities.Post;
import com.example.blogging.exceptions.ResourseNotFoundException;
import com.example.blogging.payloads.CommentDto;
import com.example.blogging.repository.CommentRepo;
import com.example.blogging.repository.PostRepo;
import com.example.blogging.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourseNotFoundException("post","postId", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourseNotFoundException("comment","commentId", commentId));
        commentRepo.delete(comment);
    }
}
