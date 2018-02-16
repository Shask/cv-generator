package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.shask.cvgenerator.model.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Log
public class HeaderGenerator implements BlockElementGenerator {

    private static final String DEFAULT_PROFIL_PIC = "./src/main/resources/profil-avatar.png";

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);

        float[] columnWidths = {3, 8, 3};
        Table table = new Table(columnWidths, true).setFontSize(PDFConstants.SMALL_FONT_SIZE).setBorder(Border.NO_BORDER);

        Optional<Image> img = ItextPDFHelper.loadImage(person.getPictureUrl(),DEFAULT_PROFIL_PIC);
        Cell imgCell = new Cell(6, 1);
        img.ifPresent(image -> imgCell.add(image.setAutoScale(true)));
        table.addCell(imgCell);

        table.addCell(ItextPDFHelper.newBasicCell(person.getFirstName() + " " + person.getSurname(), 2, 1)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(PDFConstants.MEDIUM_PLUS_FONT_SIZE)
            .setBold());

        table.addCell(ItextPDFHelper.newBasicCell(person.getDob().until(LocalDate.now(), ChronoUnit.YEARS) + " ans"));
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress1()));

        table.addCell(ItextPDFHelper.newBasicCell(person.getJobTitle(), 2, 1).setTextAlignment(TextAlignment.CENTER).setFontSize(PDFConstants.LARGE_FONT_SIZE).setBold());
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress2()));
        table.addCell(ItextPDFHelper.newBasicCell(person.getPostCode() + " " + person.getCity()));

        table.addCell(ItextPDFHelper.newBasicCell(" ", 2, 1));
        table.addCell(ItextPDFHelper.newBasicCell(person.getPhoneNumber()));
        table.addCell(ItextPDFHelper.newBasicCell(person.getEmail()));

        table.addCell(ItextPDFHelper.newBasicCell(" ", 1, 3));

        return table;
    }
}
