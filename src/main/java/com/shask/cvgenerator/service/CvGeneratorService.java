package com.shask.cvgenerator.service;

import com.shask.cvgenerator.model.Person;

import java.io.IOException;

public interface CvGeneratorService {
    String generate(Person person) throws IOException;
}
