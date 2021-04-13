package com.COMP3004CMS.cms.Model;

import com.COMP3004CMS.cms.Visitor.LogManager;
import com.COMP3004CMS.cms.Visitor.Loggable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class Student extends User implements Loggable {
}
