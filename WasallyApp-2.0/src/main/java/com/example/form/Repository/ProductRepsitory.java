package com.example.form.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.form.Entities.Product;

public interface ProductRepsitory extends CrudRepository<Product, String>{

}
