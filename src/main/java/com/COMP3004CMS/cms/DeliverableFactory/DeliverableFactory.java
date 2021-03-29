package com.COMP3004CMS.cms.DeliverableFactory;

//DeliverableFactory deliverableFactory = new DeliverableFactory();
//Deliverable deliverable = deliverableFactory.QuizDeliverable();

public class DeliverableFactory {
    public static Deliverable getType(String type) {

        Deliverable deliverable = null;

        switch (type) {
            case "QUIZ":
                deliverable = new QuizDeliverable();
                break;
            case "ASSIGNMENT":
                deliverable = new AssignmentDeliverable();
                break;
            default:
                // throw exception
                break;
        }

        return deliverable;
    }
}
