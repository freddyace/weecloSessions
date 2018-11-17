package com.weeclo.Sessions.session.repository;

//import com.weeclo.demo.entities.UserEntity;
import entities.UserEntity;

import java.util.Map;

public interface UserEntityRepository {


        void save(UserEntity user);
        Map<Integer, UserEntity> findAll();
        UserEntity findById(String id);
        void update(UserEntity user);
        void delete(String id);

}
