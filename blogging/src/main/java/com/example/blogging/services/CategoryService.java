package com.example.blogging.services;

import com.example.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
//    Create
    CategoryDto createCategory(CategoryDto categoryDto);

//    Update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

//    Delete
    void deleteCategory(Integer categoryId);

//    GetById
    CategoryDto getByIdCategory(Integer categoryId);

//     Get all data
    List<CategoryDto> getAllData();
}
