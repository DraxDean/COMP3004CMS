package com.COMP3004CMS.cms.AbstractFactoryDeliverable;

public class QuizFactory implements DeliverableFactory {
    @Override
    public Deliverable create(String courseId,String title, String start, String deadline){
        return new Quiz(courseId,title, start, deadline);
    }
}
