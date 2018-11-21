package com.shask.cvgenerator.dao;

import com.shask.cvgenerator.model.person.Person;

import java.util.Optional;

public interface PersonRepository {
    Optional<Person> get(String lastname);
}
