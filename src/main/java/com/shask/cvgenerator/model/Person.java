package com.shask.cvgenerator.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Person {
    private String firstName;
    private String surname;
    private String adress1;
    private String adress2;
    private String postCode;
    private String city;
    private String phoneNumber;
    private String email;
    private String pictureUrl;
    private LocalDate dob;
    private String jobTitle;
    private List<Experience> experiences;
    private String shortPresentation;
    private List<String> hobbies;
    private List<Language> languages;
}
