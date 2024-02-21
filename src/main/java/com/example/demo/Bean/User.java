package com.example.demo.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
}
