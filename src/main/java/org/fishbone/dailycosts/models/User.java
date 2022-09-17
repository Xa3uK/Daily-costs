package org.fishbone.dailycosts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String login;
    private String password;
    private double balance;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.balance = 0;
    }
}
