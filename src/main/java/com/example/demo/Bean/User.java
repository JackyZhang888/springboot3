package com.example.demo.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
}
