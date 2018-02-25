package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.parameter.GenerationParameters;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.HeaderGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Log
public class DefaultHeaderGenerator implements BlockElementGenerator{

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);

        String fullname = person.getFirstName() + " " + person.getSurname();
        Optional<Image> img = ItextPDFHelper.loadImage(person.getPictureUrl());


        float[] columnWidths = {4, 11, 4};
        Table table = new Table(columnWidths, true).setFontSize(PDFConstants.SMALL_FONT_SIZE).setBorder(Border.NO_BORDER);

        Cell imgCell = new Cell(6, 1)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);

        img.ifPresent(image -> imgCell.add(image.setMaxHeight(100).setMaxWidth(100)));
        table.addCell(imgCell);

        table.addCell(ItextPDFHelper.newBasicCell(fullname, 2, 1)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE)
                .setBold());

        table.addCell(ItextPDFHelper.newBasicCell(person.getDob() != null ? person.getDob().until(LocalDate.now(), ChronoUnit.YEARS) + " ans" : ""));
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress1()));


        table.addCell(ItextPDFHelper.newBasicCell(person.getStatus(), 2, 1).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP));
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress2()));
        table.addCell(ItextPDFHelper.newBasicCell(person.getPostCode() + " " + person.getCity()));

        table.addCell(ItextPDFHelper.newBasicCell(person.getJobTitle(), 2, 1).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP).setFontSize(PDFConstants.LARGE_FONT_SIZE).setBold());
        table.addCell(ItextPDFHelper.newBasicCell(person.getPhoneNumber()));
        table.addCell(ItextPDFHelper.newBasicCell(""));


        table.addCell(buildExternalLinkCell("./src/main/resources/github.png", "https://github.com/Shask", "github : Shask"));
        table.addCell(ItextPDFHelper.newBasicCell(" portefolio.io", 1, 1).setTextAlignment(TextAlignment.CENTER));
        table.addCell(ItextPDFHelper.newBasicCell(" steven.fougeron@gmail.com", 1, 1));

        return table;
    }


    //TODO Alignement des lien
    // TODO finir la partie sous le header
    private Cell buildExternalLinkCell(String logoUrl, String urlToLink, String text) {
        final float[] columnWidths = {1, 9};
        Table table = new Table(columnWidths, false).setBorder(Border.NO_BORDER).setMargin(0).setPadding(0);

        Optional<Image> logo = ItextPDFHelper.loadImage(logoUrl);
        if (logo.isPresent()) {
            table.addCell(new Cell().add(logo.get().setMaxHeight(12).setMaxHeight(12)).setBorder(Border.NO_BORDER));
        } else {
            table.addCell(new Paragraph("").setBorder(Border.NO_BORDER));
        }

        table.addCell(new Cell().add(new Paragraph(text).setAction(PdfAction.createURI(urlToLink)).setUnderline()).setBorder(Border.NO_BORDER));

        return new Cell().add(table).setBorder(Border.NO_BORDER);
    }


}
