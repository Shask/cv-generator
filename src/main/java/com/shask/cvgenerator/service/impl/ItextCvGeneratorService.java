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
import com.shask.cvgenerator.dao.PersonDao;
import com.shask.cvgenerator.exception.PersonNotFoundException;
import com.shask.cvgenerator.model.Person;
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
import java.util.Optional;

@Service
public class ItextCvGeneratorService implements CvGeneratorService {

    private PersonDao personDao;

    private static PdfFont fontHelveticaNueue;
    private static PdfFont fontHelvetica;
    private static PdfFont fontHelveticaBold;

    private BlockElementGenerator headerGenerator = new HeaderGenerator();
    private BlockElementGenerator shortOverviewGenerator = new ShortOverviewGenerator();
    private BlockElementGenerator workExperienceGenerator = new WorkExperienceGenerator();
    private BlockElementGenerator universityExperienceGenerator = new UniversityExperienceGenerator();
    private BlockElementGenerator languageAndHobbiesGenerator = new LanguageAndHobbiesGenerator();

    private static LineSeparator lineSeparator;

    public ItextCvGeneratorService() throws IOException {
        fontHelveticaNueue = PdfFontFactory.createFont(PDFConstants.FONT, true);
        fontHelvetica = PdfFontFactory.createFont("Helvetica");
        fontHelveticaBold = PdfFontFactory.createFont("Helvetica-Bold");

        lineSeparator = new LineSeparator(new RhumbusLineSeparator());
    }

    public String generate(String filepath,String surname) throws IOException {
        Objects.requireNonNull(filepath);
        Objects.requireNonNull(surname);

        Person person = personDao.get(surname).orElseThrow(PersonNotFoundException::new);

        File file = new File(filepath);
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filepath));
        pdfDoc.setDefaultPageSize(PageSize.A4);

        Document document = new Document(pdfDoc);
        document.setFont(fontHelveticaNueue);

        document.add(headerGenerator.generateFor(person));
        addSpacer(document);

        document.add(shortOverviewGenerator.generateFor(person));
        addSpacer(document);

        document.add(new Paragraph("Experience").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE));
        document.add(workExperienceGenerator.generateFor(person));
        addSpacer(document);

        document.add(new Paragraph("Scolarité").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE));
        document.add(universityExperienceGenerator.generateFor(person));
        addSpacer(document);


        document.add(new Paragraph("Langues et Loisirs").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE));
        document.add(languageAndHobbiesGenerator.generateFor(person));



        pdfDoc.close();

        return filepath;
    }

    private void addSpacer(Document doc) {
        doc.add(new Paragraph());
        doc.add(lineSeparator);
        doc.add(new Paragraph());
    }

    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
}
