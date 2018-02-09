package com.shask.cvgenerator.dao.mapping;

import com.shask.cvgenerator.model.Person;

import java.util.List;

public class PersonWrapper {
    private List<Person> allUsers;

    public PersonWrapper(List<Person> allUsers) {
        this.allUsers = allUsers;
    }

    public PersonWrapper() {
    }


    public List<Person> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Person> allUsers) {
        this.allUsers = allUsers;
    }
}