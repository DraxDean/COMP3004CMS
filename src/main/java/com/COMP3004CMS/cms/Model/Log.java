package com.COMP3004CMS.cms.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*
    Log class meant for database to fulfill visitor
*/

@Document(collection = "log")
public class Log {

    // variables
    @Id
    public String logid;       //for MongoDB
    public String userid;
    public String timestamp;

    // constructors
    public Log() {
    }

    public Log(String logd, String userd, String timestampd) {
        this.logid = logd;
        this.userid = userd;
        this.timestamp = timestampd;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;
        if (obj instanceof Log){
            Log ptr = (Log) obj;
            retVal = ptr.logid.equals(this.logid);
        }
        return retVal;
    }

    @Override
    public String toString() {
        return timestamp + " (user: " + userid + " logid: " + logid+")";
    }
}
