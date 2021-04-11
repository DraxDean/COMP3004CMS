package com.COMP3004CMS.cms.AbstractFactoryDeliverable;

public interface DeliverableFactory {


    Deliverable create(String courseId, String title, String start, String deadline);
}
