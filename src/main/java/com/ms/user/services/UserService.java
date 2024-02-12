package com.ms.user.services;

import com.ms.user.domain.User;
import com.ms.user.producer.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserProducer producer;

    @Transactional
    public User save(User user){
        User newUser = this.repository.save(user);
        producer.publishMessageEmail(newUser);
        return newUser;
    }

}
