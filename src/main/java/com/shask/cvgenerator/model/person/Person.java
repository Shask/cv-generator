package com.shask.cvgenerator.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shask.cvgenerator.model.parameter.GenerationParameters;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
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
    @JsonProperty("technologies")
    private List<Technology> skillset;
    private String github;
    private String portefolioLink;

    private Person anonymise() {
        surname = surname != null && surname.length() > 1 ? surname.substring(0, 1) + "." : "";
        adress1 = "";
        adress2 = "";
        postCode = "";
        phoneNumber = "";
        email = "";
        city = "";
        pictureUrl = "";
        dob = null;
        github="";
        portefolioLink="";
        return this;
    }

    public Person customiseWith(String companyLogoUrl, String companyName) {
        if (! StringUtils.isEmpty(companyLogoUrl)) {
            pictureUrl = companyLogoUrl;
        }
        if (! StringUtils.isEmpty(companyName)) {
            status += " avec " + companyName;
        }
        return this;
    }

    public Person customiseWith(GenerationParameters generationParams) {
        if (generationParams.isAnonymous()) {
            this.anonymise();
        }
        return this.customiseWith(generationParams.getCompanyLogoUrl(), generationParams.getCompanyName());
    }
}
