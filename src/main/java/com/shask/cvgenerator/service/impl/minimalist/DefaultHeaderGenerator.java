package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import static com.shask.cvgenerator.util.PDFConstants.EMAIL_ICON;
import static com.shask.cvgenerator.util.PDFConstants.GITHUB_ICON;
//import static com.shask.cvgenerator.util.PDFConstants.PORTEFOLIO_ICON;

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


        table.addCell(ItextPDFHelper.newHyperLinkWithLogoCell(GITHUB_ICON, "https://github.com/Shask", "github : Shask"));
        table.addCell(ItextPDFHelper.newHyperLinkCenteredCell("http://localhost:4200","portefolio"));
        table.addCell(ItextPDFHelper.newHyperLinkWithLogoCell(EMAIL_ICON, null, "steven.fougeron@gmail.com"));

        return table;
    }
}
