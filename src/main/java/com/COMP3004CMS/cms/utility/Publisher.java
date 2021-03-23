package com.COMP3004CMS.cms.utility;

public interface Publisher {

    public void subscribe(Subscriber s);
    public void unsubscribe(Subscriber s);

}
