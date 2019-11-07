package com.anhtu.entity;

import com.anhtu.encryption.PasswordEncryption;
import com.anhtu.validator.UniqueLogin;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @Length(max = 100)
    private String login;

    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(max = 100)
    private String surname;

    @NotNull
    @Size(min = 6, max = 15)
    private String password;

    public User(String login, String name, String surname, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public User() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User == false)
            return false;

        User user = (User) obj;
        return (user.getLogin().equals(login) && user.getPassword().equals(getPassword()));
    }
}
