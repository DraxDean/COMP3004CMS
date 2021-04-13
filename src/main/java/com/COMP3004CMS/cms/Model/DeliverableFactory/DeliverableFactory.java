package com.COMP3004CMS.cms.Model.DeliverableFactory;

//DeliverableFactory deliverableFactory = new DeliverableFactory();
//Deliverable deliverable = deliverableFactory.QuizDeliverable();

public class DeliverableFactory {

    public static Deliverable createByType(String type, Deliverable deliverable) {
        Deliverable deliverable1 = null;
        switch (type) {
            case "SHORT":
                deliverable1 = new ShortDeliverable();
                break;
            case "LONG":
                deliverable1 = new LongDeliverable();
                break;
            default:
                // throw exception
                break;
        }
        deliverable1.setTitle(deliverable.getTitle());
        deliverable1.setDeadline(deliverable.getDeadline());
        deliverable1.setStart(deliverable.getStart());
        deliverable1.setGrade(deliverable.getGrade());
        deliverable1.setRequirements(deliverable.getRequirements());

        return deliverable1;
    }
}
