package com.example.blogging.services.ServiceImpl;

import com.example.blogging.entities.Category;
import com.example.blogging.entities.User;
import com.example.blogging.exceptions.ResourseNotFoundException;
import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.repository.CategoryRepo;
import com.example.blogging.services.CategoryService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category user = DtoToCategory(categoryDto);
        Category  savedUser = categoryRepo.save(user);
        return categoryToDto(savedUser);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("Category","Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category","categoryId", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getByIdCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category","categoryId", categoryId));
        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllData() {
        List <Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos =  categories.stream().map((category) -> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    private CategoryDto categoryToDto(Category category)
    {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private  Category DtoToCategory(CategoryDto categoryDto)
    {
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }

}
