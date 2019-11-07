package com.anhtu.repository;

import com.anhtu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    List<User> findAll();
    User findByLogin(String login);

    List<User> deleteByLogin(String login);
}

