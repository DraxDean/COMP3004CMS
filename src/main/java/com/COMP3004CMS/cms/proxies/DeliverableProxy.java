package com.COMP3004CMS.cms.proxies;


import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.Service.DeliverableService;

public class DeliverableProxy extends DeliverableService {

    public String id;
    public String courseid;
    String title;
    String start;
    String deadline;
    // these two will definitely have to evolve into file-readers
    String requirements;


    public Deliverable get(){
        return super.findDeliverableByDeliverableid(id);
    }




}
