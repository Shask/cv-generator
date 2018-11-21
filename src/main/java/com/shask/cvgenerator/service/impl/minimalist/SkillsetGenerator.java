package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.model.person.Technology;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.util.PDFConstants;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class SkillsetGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {

        Table table = new Table(1, true)
            .setBorder(Border.NO_BORDER)
            .setFontSize(PDFConstants.MEDIUM_FONT_SIZE)
            .setMargin(0)
            .setPadding(0);

        final Map<String, String> skillByTypeMap = person.getSkillset().stream()
            .collect(Collectors.groupingBy(Technology::getType,
                collectingAndThen(toList(),
                    list -> list.stream().map(Technology::getName)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.joining(", ")))));

        skillByTypeMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> newCellWithTitle(entry.getKey(), entry.getValue()))
                .forEach(table::addCell);

        return table;
    }

    private static Cell newCellWithTitle(String title, String content) {
        final float[] columnWidths = {1, 9};
        Table table = new Table(columnWidths, false).setBorder(Border.NO_BORDER).setMargin(0).setPadding(0);

        table.addCell(new Cell().add(new Paragraph(title).setBold()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(content)).setBorder(Border.NO_BORDER));
        return new Cell().add(table).setBorder(Border.NO_BORDER);
    }
}
