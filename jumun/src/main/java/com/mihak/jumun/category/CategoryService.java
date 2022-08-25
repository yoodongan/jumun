package com.mihak.jumun.category;

import com.mihak.jumun.category.form.CategoryForm;
import com.mihak.jumun.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void create(CategoryForm categoryForm) {
        Category newCate = new Category();
        newCate.setName(categoryForm.getName());
        categoryRepository.save(newCate);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no %d category not found,".formatted(id)));
    }
}