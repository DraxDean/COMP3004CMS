package com.COMP3004CMS.cms.FactoryMethodDeliverable;

public class LongFactory implements DeliverableFactory{
    @Override
    public Deliverable create(String title, String start, String deadline){
        return new LongDeliverable(title, start, deadline);
    }
}
