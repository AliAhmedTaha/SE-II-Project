package com.example.form.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.form.Entities.User;

public interface UserRepository extends CrudRepository<User, String>{

}
