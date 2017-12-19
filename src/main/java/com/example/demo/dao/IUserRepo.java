package com.example.demo.dao;

import com.example.demo.models.enteties.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends CrudRepository<User, Long> {
    User findByName(String name);
}

