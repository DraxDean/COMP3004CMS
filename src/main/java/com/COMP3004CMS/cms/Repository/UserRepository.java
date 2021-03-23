package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
