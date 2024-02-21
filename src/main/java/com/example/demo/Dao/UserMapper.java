package com.example.demo.Dao;

import com.example.demo.Bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    User getUserByName(@Param("username") String username);
}
