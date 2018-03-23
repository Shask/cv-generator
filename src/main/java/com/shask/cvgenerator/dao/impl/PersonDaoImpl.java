package com.shask.cvgenerator.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.shask.cvgenerator.dao.PersonDao;
import com.shask.cvgenerator.dao.mapping.PersonData;
import com.shask.cvgenerator.model.person.Person;
import lombok.extern.java.Log;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
@Log
public class PersonDaoImpl implements PersonDao {

    private static final MediaType JSON = MediaType.parse("application/json");

    @Value("${graphcool.url}")
    private String API_URL;

    @Value("${graphcool.token}")
    private String AUTH_TOKEN;


    private static OkHttpClient client = new OkHttpClient();
    private static ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @Override
    public Optional<Person> get(String lastname) {
        String getPerson = "{\"query\":" +
                "\"query getUser{ " +
                " User(surname:\\\""+lastname+"\\\"){" +
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


        RequestBody body = RequestBody.create(JSON, getPerson);
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", AUTH_TOKEN)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            PersonData p = mapper.readValue(s, PersonData.class);
            return Optional.of(p.getData().getPerson());

        } catch (IOException e) {
            log.severe("Couldn't retrieve users");
            log.severe(e.toString());
            return Optional.empty();
        }

    }




}

