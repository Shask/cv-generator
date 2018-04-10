package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import static com.shask.cvgenerator.util.PDFConstants.*;

@Log
public class DefaultHeaderGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);

        String fullname = person.getFirstName() + " " + person.getSurname();
        Optional<Image> img = ItextPDFHelper.loadImage(person.getPictureUrl());


        float[] columnWidths = {1, 3, 1};
        Table table = new Table(columnWidths,true).setFontSize(PDFConstants.SMALL_FONT_SIZE).setBorder(Border.NO_BORDER);

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
        table.addCell(ItextPDFHelper.newEmptyCell());
        table.addCell(ItextPDFHelper.newBasicCell(person.getPostCode() + " " + person.getCity()));

        table.addCell(ItextPDFHelper.newBasicCell(person.getJobTitle(), 2, 1).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP).setFontSize(PDFConstants.LARGE_FONT_SIZE).setBold());
        table.addCell(ItextPDFHelper.newBasicCell(person.getPhoneNumber()));
        table.addCell(ItextPDFHelper.newHyperLinkWithLogoCell(EMAIL_ICON, null, "steven.fougeron@gmail.com"));


        table.addCell(ItextPDFHelper.newHyperLinkWithLogoCell(GITHUB_ICON, person.getGithubUrl(), "github : Shask"));
        table.addCell(ItextPDFHelper.newHyperLinkCenteredCell(person.getWebsiteUrl(), person.getWebsiteUrl()));
        table.addCell(ItextPDFHelper.newHyperLinkWithLogoCell(LINKEDIN_ICON, person.getLinkedinUrl(), "/in/stevenfougeron"));

        return table;
    }
}
