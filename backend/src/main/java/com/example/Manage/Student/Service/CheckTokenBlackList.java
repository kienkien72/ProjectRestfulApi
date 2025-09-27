package com.example.Manage.Student.Service;

import org.springframework.stereotype.Service;

import com.example.Manage.Student.Entity.BlackListToken;
import com.example.Manage.Student.Repository.BlackListRepository;

@Service
public class CheckTokenBlackList {
    private BlackListRepository blackListRepository;

    public CheckTokenBlackList(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public boolean isBlackListToken(String token) {
        return blackListRepository.findByToken(token).isPresent();
    }
}
