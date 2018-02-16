package com.shask.cvgenerator.dao;

import com.shask.cvgenerator.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDaoTest {

    private PersonDao personDao;


    @Test
    public void getTest() {
        Optional<Person> p = personDao.get("Fougeron");
        Assert.assertNotNull(p);
        Assert.assertTrue(p.isPresent());
        Assert.assertTrue(p.get().getFirstName() != null && !p.get().getFirstName().equals(""));
    }

    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
}
