package com.example.blogging.controllers;

import com.example.blogging.payloads.ApiResponse;
import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    Create
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategory =   categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

//    Update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
    {
        CategoryDto updatedCategory =   categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

//    Delete
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category is Deleted successfully", true), HttpStatus.OK);
    }

//    Get
    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable Integer categoryId)
    {
       CategoryDto categoryDto =  categoryService.getByIdCategory(categoryId);
       return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

//    Get All data
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllData()
    {
        List<CategoryDto> categoryDtos =  categoryService.getAllData();
        return ResponseEntity.ok(categoryDtos);
    }

}
