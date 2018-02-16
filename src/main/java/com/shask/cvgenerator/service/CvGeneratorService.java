package com.shask.cvgenerator.service;

import java.io.IOException;

public interface CvGeneratorService {
    String generate(String filepath,String surname) throws IOException;
}
