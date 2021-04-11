package com.COMP3004CMS.cms.DeliverableFactory;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

//to override sth
public class LongDeliverable extends com.COMP3004CMS.cms.Model.Deliverable {
    boolean longanwser;


    // constructors
    public LongDeliverable() {
        longanwser = true;
    }


}


