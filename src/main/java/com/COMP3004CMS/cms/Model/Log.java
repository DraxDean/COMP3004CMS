package com.COMP3004CMS.cms.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*
    Log class meant for database to fulfill visitor
*/

@Document(collection = "log")
public class Log {

    // variables
    public static int staticId;
    @Id
    public int logid;       //for MongoDB
    public String id;
    public String timestamp;

    // constructors
    public Log() {
    }

    public Log(String id, String timestampd) {
        this.logid = staticId++;
        this.id = id;
        this.timestamp = timestampd;
    }

    @Override
    public String toString() {
        return timestamp + " (user: " + id + " logid: " + logid+")";
    }
}
