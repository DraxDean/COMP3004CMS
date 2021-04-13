package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.DeliverableFactory.Deliverable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliverableRepository extends MongoRepository<Deliverable, String> {
    Deliverable findDeliverableByDeliverableid(String deliverableid);
    void deleteDeliverableByDeliverableid(String deliverableid);
}
