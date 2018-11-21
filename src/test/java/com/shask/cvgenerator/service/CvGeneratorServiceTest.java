package com.shask.cvgenerator.service;

import com.shask.cvgenerator.dao.PersonRepository;
import com.shask.cvgenerator.exception.PersonNotFoundException;
import com.shask.cvgenerator.model.parameter.GenerationParameters;
import com.shask.cvgenerator.model.person.*;
import com.shask.cvgenerator.service.impl.ItextCvGeneratorService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CvGeneratorServiceTest {


    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CvGeneratorService cvGeneratorService = new ItextCvGeneratorService();

    private Person me;
    private String lastname = "Feureong";
    private final String fileURI = "./test.pdf";

    private GenerationParameters parameters;

    public CvGeneratorServiceTest() throws IOException {
    }

    @Before
    public void before() {
        parameters = new GenerationParameters(true,"French Corp. IO","./src/test/resources/tallLogo.png");

        List<ExperienceTranslation> fr = Collections.singletonList(ExperienceTranslation.builder()
                .language("FR")
                .longDescription(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .position("Etudiant")
                .project("Master Informatique et bla")
                .shortDescription("Saepissime igitur mihi de amicitia cogitanti maxime illud considerandum videri solet, utrum propter imbecillitatem atque")
                .build());

        List<ExperienceTranslation> fr2 = Collections.singletonList(ExperienceTranslation.builder()
                .language("FR")
                .longDescription(
                        "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? ")
                .position("Dévelopeur FullStack")
                .project("Master Informatique et bla")
                .shortDescription("De Finibus Bonorum et Malorum\" de Ciceron (45 av. J.-C.)")
                .build());

        Establishment excilys = new Establishment("", "Excïlys");
        Establishment laSofia = new Establishment("", "La Søfia");
        Establishment unilim = new Establishment("", "Univérsithé`è du Limousin");

        List<Experience> experiences = Lists.newArrayList(Experience.builder()
                        .dateBegin(LocalDate.of(2013, Month.APRIL, 15))
                        .dateEnd(LocalDate.of(2013, Month.MAY, 20))
                        .establishment(excilys)
                        .location("Paris")
                        .type(ExperienceType.Work)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))
                        .experienceTranslations(fr2)
                        .build(),

                Experience.builder()
                        .dateBegin(LocalDate.of(2014, Month.JANUARY, 29))
                        .dateEnd(LocalDate.of(2014, Month.DECEMBER, 1))
                        .establishment(laSofia)
                        .location("Paris")
                        .type(ExperienceType.Work)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))
                        .experienceTranslations(fr2)
                        .build(),

                Experience.builder()
                        .dateBegin(LocalDate.of(2013, Month.APRIL, 15))
                        .dateEnd(LocalDate.of(2014, Month.JANUARY, 10))
                        .type(ExperienceType.Work)
                        .location("Paris")
                        .establishment(new Establishment("", "Excilys"))
                        .experienceTranslations(fr)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))

                        .build(),

                Experience.builder()
                        .dateBegin(LocalDate.of(2013, Month.APRIL, 15))
                        .dateEnd(LocalDate.of(2013, Month.APRIL, 15))
                        .establishment(excilys)
                        .location("Paris")
                        .type(ExperienceType.Work)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))
                        .experienceTranslations(fr2)
                        .build(),

                Experience.builder()
                        .dateBegin(LocalDate.of(2012, Month.JANUARY, 1))
                        .dateEnd(LocalDate.of(2013, Month.JUNE, 15))
                        .establishment(unilim)
                        .location("Limoges")
                        .type(ExperienceType.University)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))
                        .experienceTranslations(fr)
                        .build(),

                Experience.builder()
                        .dateBegin(LocalDate.of(2010, Month.JANUARY, 1))
                        .dateEnd(LocalDate.of(2012, Month.JUNE, 15))
                        .establishment(unilim)
                        .location("Limoges")
                        .type(ExperienceType.University)
                        .experienceTranslations(fr)
                        .technologies(Lists.newArrayList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                                new Technology("IntelIJ","IDE")))
                        .build());


        me = Person.builder()
                .city("Paris")
                .postCode("750011")
                .adress1("516 zqdqzd zqdqz qzrqze")
                .adress2("Appt - 1585 Bis")
                .dob(LocalDate.of(1992, Month.JUNE, 5))
                .email("zeqionoqn@gmail.com")
                .jobTitle("Developeur MOUSSE TACHE (et barbe)")
                .firstName("Stephane")
                .surname(lastname)
                .pictureUrl("http://78.media.tumblr.com/3d7038d69d29fd059404629d616e93b6/tumblr_mqnbjkIKDi1s1pua7o1_500.png")
                .phoneNumber("(+34) 7 78 98 54 21")
                .experiences(experiences)
                .status("Freelance")
                .skillset(Arrays.asList(new Technology("Java","Langage"), new Technology("Spring","Frame"), new Technology("Hibernate","ORM"), new Technology("Javascript","Langage"),
                        new Technology("IntelIJ","IDE")))
                .shortPresentation("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                .languages(Lists.newArrayList(new Language("French", "fluent"), new Language("English", "native"), new Language("Swedish", "intermediate")))
                .hobbies(Lists.newArrayList("Boxe Francaise", "Jeux de stratégie en temps réel", "Latin oriental", "Voyage"))
                .build();
    }

    @Test(expected = PersonNotFoundException.class)
    public void testCvGenerationPersonNotFound() throws IOException {
        when(personRepository.get(lastname)).thenReturn(Optional.empty());
        cvGeneratorService.generate(fileURI,lastname,parameters);
    }


    @Test(expected = NullPointerException.class)
    public void testCvGenerationFilenull() throws IOException {
        cvGeneratorService.generate(null,lastname,parameters);
    }

    @Test(expected = NullPointerException.class)
    public void testCvGenerationLastnameNull() throws IOException {
        cvGeneratorService.generate(fileURI,null,parameters);
    }

}