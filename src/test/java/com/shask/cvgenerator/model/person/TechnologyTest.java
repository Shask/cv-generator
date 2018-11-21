package com.shask.cvgenerator.model.person;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TechnologyTest {

    @Test
    public void should_be_sorted_by_type_then_name() {
        //given
        List<Technology> technologyList = Arrays.asList(
                new Technology("Java","Langage","","",true),
                new Technology("TypeScript","Langage","","",true),
                new Technology("Hibernate","ORM","","",true),
                new Technology("Spring","Framework","","",true),
                new Technology("Lombok","Librairie","","",true),
                new Technology("Feign","Librairie","","",true));

        //when
        technologyList.sort(Technology::compareTo);

        //then
        assertThat(technologyList.get(0).getName(),is("Spring"));
        assertThat(technologyList.get(1).getName(),is("Java"));
        assertThat(technologyList.get(2).getName(),is("TypeScript"));
        assertThat(technologyList.get(3).getName(),is("Feign"));
        assertThat(technologyList.get(4).getName(),is("Lombok"));
        assertThat(technologyList.get(5).getName(),is("Hibernate"));

    }
}