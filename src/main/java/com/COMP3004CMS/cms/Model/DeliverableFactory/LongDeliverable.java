package com.COMP3004CMS.cms.Model.DeliverableFactory;

import com.COMP3004CMS.cms.Model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

//to override sth
@Document(collection = "deliverable")
@TypeAlias("long")
public class LongDeliverable extends Deliverable{
    // deliverable variables
    // constructors
    public LongDeliverable() {
    }
}
