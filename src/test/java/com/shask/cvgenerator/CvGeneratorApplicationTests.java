package com.shask.cvgenerator;

import com.shask.cvgenerator.model.parameter.GenerationParameters;
import com.shask.cvgenerator.service.CvGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CvGeneratorApplicationTests {


    @Autowired
    CvGeneratorService cvGeneratorService;

    @Test
    public void contextLoads() throws IOException {

        cvGeneratorService.generate("./testRealData.pdf", "Fougeron", new GenerationParameters(false, "", ""));
    }

}
