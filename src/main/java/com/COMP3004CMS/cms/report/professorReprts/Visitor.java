package com.COMP3004CMS.cms.report.professorReprts;


import com.COMP3004CMS.cms.DeliverableFactory.ShortDeliverable;
import com.COMP3004CMS.cms.FactoryMethodDeliverable.LongDeliverable;

import java.util.HashMap;

public interface Visitor {
    HashMap<String, Double> visitLongDeliverable(LongDeliverable ld);
    //public HashMap<String, Double> visitShortDeliverable( ShortDeliverable sd);
}
