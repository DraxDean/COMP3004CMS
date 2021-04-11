package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.Repository.DeliverableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverableService {
    @Autowired
    DeliverableRepository deliverableRepository;

    public void save(Deliverable deliverable) {
        deliverableRepository.save(deliverable);
    }

    public Deliverable findDeliverableByDeliverableid(String id){
        return deliverableRepository.findDeliverableByDeliverableid(id);
    }

    public void deleteDeliverableByDeliverableid(String id){
        deliverableRepository.deleteDeliverableByDeliverableid(id);
    }

}
