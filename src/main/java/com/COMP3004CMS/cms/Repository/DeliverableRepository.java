package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.Deliverable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliverableRepository extends MongoRepository<Deliverable, String> {
}