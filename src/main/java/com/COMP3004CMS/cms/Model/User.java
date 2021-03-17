package com.COMP3004CMS.cms.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    public String id;

    public String username;
    public String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "[id=%s, username='%s']",
                id, username);
    }

}
