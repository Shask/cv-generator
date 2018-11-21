package com.shask.cvgenerator.dao;

import com.shask.cvgenerator.model.person.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    private PersonRepository personRepository;


    @Test
    public void getTest() {
        Optional<Person> p = personRepository.get("Fougeron");
        Assert.assertNotNull(p);
        Assert.assertTrue(p.isPresent());
        Assert.assertTrue(p.get().getFirstName() != null && !p.get().getFirstName().equals(""));
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
