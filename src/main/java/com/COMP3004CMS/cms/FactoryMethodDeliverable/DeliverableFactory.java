package com.COMP3004CMS.cms.FactoryMethodDeliverable;

public interface DeliverableFactory {


    Deliverable create(String title, String start, String deadline);
}
