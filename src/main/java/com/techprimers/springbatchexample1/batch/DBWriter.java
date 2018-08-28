package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.User;
import com.techprimers.springbatchexample1.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {

        System.out.println("Data Saved for Users: " + users);
        List<Object> data_not_valid_ = users.stream()
                .map(
                        dept -> {

                                if (dept.getDept() == null)
                                    return Optional.empty();
                            else
                            return dept ;}
                        ).collect(Collectors.toList());{
            if (data_not_valid_.contains(Optional.empty()) || data_not_valid_.size()!=users.size()) {
                System.out.println("data not valid ");}
                else {
                userRepository.save(users);}

        }}


}
