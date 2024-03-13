package com.example.blogging.payloads;

import com.example.blogging.entities.Category;
import com.example.blogging.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String postId;
    private String title;
    private String content;
    private String imageName;
    private Date date;
    private CategoryDto category;
    private UserDto user;

}
