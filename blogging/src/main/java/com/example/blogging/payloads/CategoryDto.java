package com.example.blogging.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty(message = "Title must not be Empty")
    private String categoryTitle;
    @NotEmpty(message = "Description must have minimum 5 characters")
    @Size(min = 5)
    private String categoryDescription;
}
