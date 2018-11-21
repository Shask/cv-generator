package com.shask.cvgenerator.service.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.shask.cvgenerator.dao.PersonRepository;
import com.shask.cvgenerator.exception.PersonNotFoundException;
import com.shask.cvgenerator.model.parameter.GenerationParameters;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.CvGeneratorService;
import com.shask.cvgenerator.service.impl.minimalist.*;
import com.shask.cvgenerator.service.impl.util.RhumbusLineSeparator;
import com.shask.cvgenerator.util.PDFConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.shask.cvgenerator.util.PDFConstants.FONT_HELVETIVA_NUEUE;

@Service
public class ItextCvGeneratorService implements CvGeneratorService {

    private PersonRepository personRepository;

    private BlockElementGenerator headerGenerator = new DefaultHeaderGenerator();
    private BlockElementGenerator annonymousHeaderGenerator = new AnnonymousHeaderGenerator();
    private BlockElementGenerator shortOverviewGenerator = new ShortOverviewGenerator();
    private BlockElementGenerator workExperienceGenerator = new WorkExperienceGenerator();
    private BlockElementGenerator universityExperienceGenerator = new UniversityExperienceGenerator();
    private BlockElementGenerator languageAndHobbiesGenerator = new LanguageAndHobbiesGenerator();
    private BlockElementGenerator skillsetGenerator= new SkillsetGenerator();

    private static LineSeparator lineSeparator;

    public ItextCvGeneratorService() throws IOException {
        lineSeparator = new LineSeparator(new RhumbusLineSeparator());
    }

    public String generate(String filepath, String surname, GenerationParameters generationParameters) throws IOException {
        Objects.requireNonNull(filepath);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(generationParameters);

        //Retreive the person
        Person person = personRepository.get(surname).orElseThrow(PersonNotFoundException::new);
        person = person.customiseWith(generationParameters);

        //create file path to futur pdf doc
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        //Create pdf doc
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filepath));
        pdfDoc.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDoc);
        document.setFont(FONT_HELVETIVA_NUEUE.get());

        addHeader(document,generationParameters,person);

        addElement("Compétences",skillsetGenerator,true,person,document);
        addElement("Experiences",workExperienceGenerator,true,person,document);
        addElement("Scolarité",universityExperienceGenerator,true,person,document);
        addElement("Langues et Loisirs",languageAndHobbiesGenerator,false,person,document);


        pdfDoc.close();

        return filepath;
    }

    private void addHeader(Document document,GenerationParameters generationParameters, Person person)
    {
        if (generationParameters.isAnonymous()) {
            document.add(annonymousHeaderGenerator.generateFor(person));
        } else {
            document.add(headerGenerator.generateFor(person));
        }
        addSpacer(document);

        if (person.getShortPresentation() != null && !person.getShortPresentation().trim().isEmpty()) {
            document.add(shortOverviewGenerator.generateFor(person));
            addSpacer(document);
        }
    }

    private void addElement(String title, BlockElementGenerator generator, boolean withSpacer, Person person, Document document) {
        document.add(getTitle(title));
        document.add(generator.generateFor(person));
        if (withSpacer) {
            addSpacer(document);
        }
    }

    private void addSpacer(Document doc) {
        doc.add(new Paragraph());
        doc.add(lineSeparator);
        doc.add(new Paragraph());
    }

    private Paragraph getTitle(String title) {
        return new Paragraph(title).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE);
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
