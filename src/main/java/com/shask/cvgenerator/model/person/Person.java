package com.shask.cvgenerator.model.person;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
public class Person {
    private String firstName;
    private String surname;
    private String status;
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

    public Person anonymise(String companyLogoUrl, String companyName) {
        Objects.requireNonNull(companyLogoUrl);
        Objects.requireNonNull(companyName);

        surname = surname != null && surname.length() > 1 ? surname.substring(0, 1) + "." : "";
        adress1 = "";
        adress2 = "";
        postCode = "";
        phoneNumber = "";
        email = "";
        pictureUrl = companyLogoUrl;
        status += " " + companyName;
        city = "";
        dob = null;
        return this;
    }
}
