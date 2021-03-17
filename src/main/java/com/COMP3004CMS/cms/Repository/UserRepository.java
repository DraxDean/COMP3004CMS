package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findByUsername(String username);

}
