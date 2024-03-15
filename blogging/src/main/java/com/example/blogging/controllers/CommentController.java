package com.example.blogging.controllers;


import com.example.blogging.payloads.ApiResponse;
import com.example.blogging.payloads.CommentDto;
import com.example.blogging.services.CommentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/comments")
public class CommentController {

    @Autowired
    public CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId)
    {
        CommentDto savedComment =  commentService.createComment(commentDto, postId);
        System.out.println(savedComment.getComment());
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId)
    {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }
}
