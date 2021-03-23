package com.COMP3004CMS.cms.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
@Document(collection = "users")
public class User {
    @Id
    public String id;
    public String username;
    public String password;
    private String roles;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format(
                "[id=%s, username='%s']",
                id, username);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
