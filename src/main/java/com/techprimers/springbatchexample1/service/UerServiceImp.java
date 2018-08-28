package com.techprimers.springbatchexample1.service;

import com.techprimers.springbatchexample1.model.User;
import com.techprimers.springbatchexample1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UerServiceImp implements UserService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
