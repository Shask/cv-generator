package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
public class LanguageAndHobbiesGenerator implements BlockElementGenerator {


    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);

        float[] finalTableColumnWidths = {1,1};

        Table finalTable = new Table(finalTableColumnWidths, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);

        Optional<Image> img = ItextPDFHelper.loadImage( "./src/main/resources/rhombusLD.png");
        Cell imgCell=new Cell();
        img.ifPresent(image -> imgCell.add(image.setHeight(8)));

        java.util.List<Table> languagesAndHobbiesList = new ArrayList<>();

        if (person.getLanguages() != null) {
            languagesAndHobbiesList.addAll(person.getLanguages()
                .stream()
                .map(language -> getTableFormated(imgCell,language.getName() + " : " + language.getLevel()))
                .collect(Collectors.toList()));

        }
        if (person.getHobbies() != null) {
            languagesAndHobbiesList.addAll(person.getHobbies().stream()
                .map(hobbie -> getTableFormated(imgCell,hobbie))
                .collect(Collectors.toList()));
        }

        for (Table item : languagesAndHobbiesList) {
            finalTable.addCell(new Cell().add(item).setBorder(Border.NO_BORDER));
        }

        return finalTable;
    }

    private Table getTableFormated(Cell imgCell ,String libelle)
    {
        float[] innerTableColumnWidths = {1, 12};
        Table table = new Table(innerTableColumnWidths, true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);
        table.addCell(imgCell.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.RIGHT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(libelle)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.LEFT)
            .setBorder(Border.NO_BORDER));
        return table;
    }
}
