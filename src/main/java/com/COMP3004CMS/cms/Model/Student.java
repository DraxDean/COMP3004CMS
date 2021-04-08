package com.COMP3004CMS.cms.Model;

import com.COMP3004CMS.cms.Visitor.LogManager;
import com.COMP3004CMS.cms.Visitor.Loggable;
import com.COMP3004CMS.cms.utility.Subscriber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class Student extends User implements Loggable {
    public void accept(LogManager lm) {
        lm.log(this);
    }
}
