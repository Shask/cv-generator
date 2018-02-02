package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.shask.cvgenerator.model.Experience;
import com.shask.cvgenerator.model.Technology;
import com.shask.cvgenerator.util.PDFConstants;
import com.shask.cvgenerator.util.impl.FrenchPeriodFormatter;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.shask.cvgenerator.service.impl.ItextPDFHelper.newBasicCell;
import static com.shask.cvgenerator.util.PDFConstants.FONT_HELVETIVA;

public class GeneralExperienceGenerator {

    protected Table experienceListElement(List<Experience> experiences) {

        float[] columnWidths = {1, 1};
        Table finalTable = new Table(columnWidths, true).setFontSize(6).setBorder(Border.NO_BORDER);

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
        float[] innerColumnWidths = {1, 1, 1, 1, 1, 1, 1, 1};
        Table t = new Table(innerColumnWidths, true).setFontSize(5);
        t.addCell(new Cell(1, 2).add(new Paragraph(exp.getDateBegin().format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRANCE)) + " - " +
            exp.getDateEnd().format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRANCE))).setBackgroundColor(DeviceRgb.BLACK).setFontColor(DeviceRgb.WHITE))
            .setBorder(Border.NO_BORDER)
            .setTextAlignment(TextAlignment.CENTER));
        t.addCell(newBasicCell(new FrenchPeriodFormatter().format(Period.between(exp.getDateBegin(), exp.getDateEnd())), 1, 2));
        t.addCell(newBasicCell("", 1, 4));

        final int padding = 1;
        final int txtSize = innerColumnWidths.length - padding;
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getEstablishment().getName(), txtSize, PDFConstants.FONT_HELVETIVA_BOLD));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getExperienceTranslation("FR").getPosition(), txtSize, FONT_HELVETIVA));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getExperienceTranslation("FR").getLongDescription(), txtSize));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(String.join(" - ", exp.getTechnologies().stream().map(Technology::toString).collect(Collectors.toList())), txtSize).setItalic());
        return t;
    }
}
