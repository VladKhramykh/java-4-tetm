package entity;

import lombok.Data;

@Data
public class User {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
