package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList;

public interface LogRepository extends MongoRepository<Log, String> {
    void add(String log);
    ArrayList<String> getLogs();
}
