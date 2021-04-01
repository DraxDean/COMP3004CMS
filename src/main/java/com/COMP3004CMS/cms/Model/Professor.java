package com.COMP3004CMS.cms.Model;

import com.COMP3004CMS.cms.utility.Subscriber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class Professor extends User {
}
