package com.shask.cvgenerator.service;

import com.shask.cvgenerator.model.parameter.GenerationParameters;

import java.io.IOException;

public interface CvGeneratorService {
    String generate(String filepath, String surname, GenerationParameters params) throws IOException;
}
