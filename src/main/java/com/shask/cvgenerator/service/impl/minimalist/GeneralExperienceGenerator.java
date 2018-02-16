package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.Experience;
import com.shask.cvgenerator.model.Technology;
import com.shask.cvgenerator.util.PDFConstants;
import com.shask.cvgenerator.util.impl.FrenchPeriodFormatter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.shask.cvgenerator.service.impl.ItextPDFHelper.newBasicCell;
import static com.shask.cvgenerator.util.PDFConstants.FONT_HELVETIVA;
import static com.shask.cvgenerator.util.PDFConstants.FONT_HELVETIVA_BOLD;
import static com.shask.cvgenerator.util.PDFConstants.SMALL_FONT_SIZE;

class GeneralExperienceGenerator {

    private final static FrenchPeriodFormatter frenchPeriodFormatter = new FrenchPeriodFormatter();

    protected Table experienceListElement(List<Experience> experiences) {

        Objects.requireNonNull(experiences);

        float[] columnWidths = {1, 1};
        Table finalTable = new Table(columnWidths, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);

        List<Table> degrees = new ArrayList<>();

        for (Experience exp : experiences) {
            degrees.add(experienceElement(exp));
        }

        for (Table degree : degrees) {
            finalTable.addCell(new Cell().add(degree).setBorder(Border.NO_BORDER));
        }
        return finalTable;
    }

    private Table experienceElement(Experience exp) {
        Objects.requireNonNull(exp);

        float[] innerColumnWidths = {1, 1, 1, 1, 1, 1, 1, 1};
        Table t = new Table(innerColumnWidths, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE);

        String dateBegin, dateEnd;
        dateBegin = exp.getDateBegin().format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRANCE));
        if (exp.getDateEnd() != null) {
            dateEnd = exp.getDateEnd().format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRANCE));
        } else {
            dateEnd = "Aujourd'hui";
            exp.setDateEnd(LocalDate.now());
        }

        t.addCell(new Cell(1, 3).add(new Paragraph(dateBegin + " - " + dateEnd)
                .setBackgroundColor(DeviceRgb.BLACK)
                .setFontSize(SMALL_FONT_SIZE)
                .setFontColor(DeviceRgb.WHITE))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold());

        t.addCell(newBasicCell(frenchPeriodFormatter.format(Period.between(exp.getDateBegin(), exp.getDateEnd())), 1, 3));
        t.addCell(newBasicCell("", 1, 2));

        final int padding = 1;
        final int txtSize = innerColumnWidths.length - padding;
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getEstablishment().getName(), txtSize, PDFConstants.FONT_HELVETIVA_BOLD));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getExperienceTranslation("FR").getPosition(), txtSize, FONT_HELVETIVA));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getExperienceTranslation("FR").getShortDescription(), txtSize));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(String.join(" - ", exp.getTechnologies().stream().map(Technology::toString).collect(Collectors.toList())), txtSize).setItalic());
        return t;
    }
}
