package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.shask.cvgenerator.model.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import lombok.extern.java.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.shask.cvgenerator.service.impl.ItextPDFHelper.newBasicCell;

@Log
public class HeaderGenerator implements BlockElementGenerator {

    private static final String DEFAULT_PROFIL_PIC = "./src/main/resources/profil-avatar.png";

    @Override
    public BlockElement generateFor(final Person person) {
        float[] columnWidths = {3, 8, 3};
        Table table = new Table(columnWidths, true).setFontSize(5).setBorder(Border.NO_BORDER);

        Optional<Image> img = loadImage(person.getPictureUrl());
        Cell imgCell = new Cell(6, 1);
        img.ifPresent(image -> imgCell.add(image.setAutoScale(true)));
        table.addCell(imgCell);

        table.addCell(ItextPDFHelper.newBasicCell(person.getFirstName() + " " + person.getSurname(), 2, 1)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(8)
            .setBold());

        table.addCell(ItextPDFHelper.newBasicCell(person.getDob().until(LocalDate.now(), ChronoUnit.YEARS) + " ans"));
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress1()));

        table.addCell(ItextPDFHelper.newBasicCell(person.getJobTitle(), 2, 1).setTextAlignment(TextAlignment.CENTER).setFontSize(8).setBold());
        table.addCell(ItextPDFHelper.newBasicCell(person.getAdress2()));
        table.addCell(ItextPDFHelper.newBasicCell(person.getPostCode() + " " + person.getCity()));

        table.addCell(ItextPDFHelper.newBasicCell(" ", 2, 1));
        table.addCell(ItextPDFHelper.newBasicCell(person.getPhoneNumber()));
        table.addCell(ItextPDFHelper.newBasicCell(person.getEmail()));

        table.addCell(ItextPDFHelper.newBasicCell(" ", 1, 3));

        return table;
    }

    private Optional<Image> loadImage(String imgUrl) {


        try {
            return Optional.of(new Image(ImageDataFactory.create(imgUrl)));
        } catch (MalformedURLException e) {
            log.warning("Couldn't find profil image with the given url");
            try {
                return Optional.of(new Image(ImageDataFactory.create(DEFAULT_PROFIL_PIC)));
            } catch (MalformedURLException e1) {
                log.severe("Couldn't find profil image in filesystem");
            }
        }
        return Optional.empty();
    }
}
