package com.shask.cvgenerator.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shask.cvgenerator.dao.PersonRepository;
import com.shask.cvgenerator.dao.mapping.PersonData;
import com.shask.cvgenerator.model.person.Person;
import lombok.extern.java.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
@Log
public class PersonRepositoryImpl implements PersonRepository {

    @Value("${graphcool.url}")
    private String API_URL;

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public PersonRepositoryImpl(HttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }


    @Override
    public Optional<Person> get(String lastname) {
        String getPerson = "{\"query\":" +
                "\"query getUser{ " +
                " User(surname:\\\"" + lastname + "\\\"){" +
                "adress1 " +
                "adress2 " +
                "postCode " +
                "city " +
                "phoneNumber " +
                "email " +
                "pictureUrl " +
                "jobTitle " +
                "firstName " +
                "surname " +
                "shortPresentation " +
                "dob " +
                "status " +
                "githubUrl " +
                "linkedinUrl " +
                "websiteUrl " +
                " hobbies" +
                " technologies (filter: {advertised: true}){" +
                "   name" +
                "   type" +
                "   version" +
                " }" +
                "    languages {" +
                "      name" +
                "      level" +
                " }" +
                "experiences {" +
                "   location" +
                "   dateBegin" +
                "   dateEnd" +
                "   name" +
                "   technologies {" +
                "     name    " +
                "     type    " +
                "    } " +
                "  establishment {" +
                "  name" +
                "  logoUrl " +
                "  } " +
                "  type " +
                "  experienceTranslations (filter: {language: FR}) {" +
                "    language" +
                "    longDescription" +
                "    shortDescription" +
                "    position" +
                "    project " +
                "} }}}\"" +
                "}";

        StringEntity entity = new StringEntity(getPerson, ContentType.APPLICATION_JSON);
        HttpUriRequest request = RequestBuilder.post(API_URL)
                .setUri(API_URL)
                .setEntity(entity)
                .build();
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                PersonData p = mapper.readValue(response.getEntity().getContent(), PersonData.class);
                return Optional.of(p.getData().getPerson());
            } else {
                log.severe("Couldn't retrieve users");
                return Optional.empty();
            }
        } catch (IOException e) {
            log.severe("Couldn't retrieve users");
            log.severe(e.toString());
            return Optional.empty();
        }

    }


}

