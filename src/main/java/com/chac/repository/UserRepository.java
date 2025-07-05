package com.chac.repository;

import com.chac.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 可以添加自定义查询方法
    User findByUsername(String username);
    User findByEmail(String email);
} 