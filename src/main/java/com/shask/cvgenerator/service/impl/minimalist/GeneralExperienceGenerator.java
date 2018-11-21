package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.shask.cvgenerator.model.person.Experience;
import com.shask.cvgenerator.model.person.Technology;
import com.shask.cvgenerator.util.PDFConstants;
import com.shask.cvgenerator.util.impl.FrenchPeriodFormatter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.shask.cvgenerator.service.impl.ItextPDFHelper.newBasicCell;
import static com.shask.cvgenerator.util.PDFConstants.FONT_HELVETIVA;
import static com.shask.cvgenerator.util.PDFConstants.SMALL_FONT_SIZE;

class GeneralExperienceGenerator {

    private final static FrenchPeriodFormatter frenchPeriodFormatter = new FrenchPeriodFormatter();

    protected Table experienceListElement(List<Experience> experiences,final int numberOfColumns) {
        Objects.requireNonNull(experiences);

        Table finalTable = new Table(numberOfColumns, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);

        experiences.stream()
            .sorted()
            .map(this::experienceElement)
            .map(element -> new Cell().add(element).setBorder(Border.NO_BORDER))
            .forEach(finalTable::addCell);
        return finalTable;
    }

    private Table experienceElement(Experience exp) {
        Objects.requireNonNull(exp);

        float[] innerColumnWidths = {1, 1, 1, 1, 1, 1, 1, 1};
        Table t = new Table(innerColumnWidths, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setKeepTogether(true);

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
        t.addCell(newBasicCell(exp.getEstablishment().getName(), txtSize, PDFConstants.FONT_HELVETIVA_BOLD.get()));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getExperienceTranslation("FR").getPosition(), txtSize, FONT_HELVETIVA.get()));
        t.addCell(newBasicCell("", padding));
        t.addCell(formatHtmlToCell(exp.getExperienceTranslation("FR").getLongDescription(), txtSize));
        t.addCell(newBasicCell("", padding));
        t.addCell(newBasicCell(exp.getTechnologies().stream()
                .sorted()
                .map(Technology::toString)
                .collect(Collectors.joining(" - ")), txtSize)
                .setItalic());
        return t;
    }

    private Cell formatHtmlToCell(String html,int colspan) {
        String[] elements = html.split("<br>");
        Cell cell = new Cell(1,colspan).setBorder(Border.NO_BORDER);
        for (String element : elements) {
            cell.add(new Paragraph(element));
        }
        return cell;
    }
}
