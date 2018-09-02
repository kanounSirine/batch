package com.techprimers.springbatchexample1;

import com.techprimers.springbatchexample1.model.User;
import com.techprimers.springbatchexample1.model.UserConvert;
import com.techprimers.springbatchexample1.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
@RunWith(SpringRunner.class)
@DataJpaTest
public class InsertUsers {
    @Autowired
    public UserRepository userRepository;
    @Test
    public void insert()
    {
        List<User> result = userRepository.findAll();
        assertTrue(!result.isEmpty());
    }



}
