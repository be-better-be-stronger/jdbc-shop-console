package com.demo.jdbc.service.impl;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CategoryDao;
import com.demo.jdbc.dao.impl.CategoryDaoImpl;
import com.demo.jdbc.model.Category;
import com.demo.jdbc.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	private final CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Category create(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Tên danh mục không được trống");
        return dao.insert(new Category(null, name.trim()));
    }

    @Override
    public boolean update(int id, String newName) {
        return dao.update(new Category(id, newName));
    }

    @Override
    public boolean delete(int id) {
        return dao.deleteById(id);
    }
}
