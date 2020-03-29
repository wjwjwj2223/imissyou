package com.lin.imissyou.services;

import com.lin.imissyou.model.User;
import com.lin.imissyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findFirstById(id);
    }
}
