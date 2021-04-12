package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.Log;
import com.COMP3004CMS.cms.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface LogRepository extends MongoRepository<Log, String> {
    List<Log> findAll();
}
