package com.COMP3004CMS.cms.AbstractFactoryDeliverable;

public class LongFactory implements DeliverableFactory{
    @Override
    public Deliverable create(String courseId, String title, String start, String deadline){
        return new LongDeliverable(courseId, title, start, deadline);
    }
}
