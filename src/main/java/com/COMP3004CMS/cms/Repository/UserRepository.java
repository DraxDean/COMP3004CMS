package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findUserById(String id);
    List<User> findAllByRoles(String roles);

    @Override
    Optional<User> findById(String s);

    @Override
    void deleteById(String s);

    User findUserByUserid(String userid);
    void deleteUserByUserid(String userid);
}
