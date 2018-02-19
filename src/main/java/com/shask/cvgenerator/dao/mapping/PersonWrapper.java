package com.shask.cvgenerator.dao.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shask.cvgenerator.model.person.Person;
import lombok.Data;

@Data
public class PersonWrapper {
    @JsonProperty("User")
    private Person person;
}