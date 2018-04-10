package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.service.impl.ItextPDFHelper;
import com.shask.cvgenerator.util.PDFConstants;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shask.cvgenerator.util.PDFConstants.RHOMBUS_ICON;

@Log
public class LanguageAndHobbiesGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);

        Table finalTable = new Table(2).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);

        Optional<Image> img = ItextPDFHelper.loadImage( RHOMBUS_ICON);
        Cell imgCell=new Cell();
        img.ifPresent(image -> imgCell.add(image.setHeight(6)));

        java.util.List<Table> languagesAndHobbiesList = new ArrayList<>();

        if (person.getLanguages() != null) {
            languagesAndHobbiesList.addAll(person.getLanguages()
                .stream()
                .map(language -> getTableFormated(imgCell,language.getName() + " : " + language.getLevel()))
                .collect(Collectors.toList()));

        }
        if (person.getHobbies() != null) {
            languagesAndHobbiesList.addAll(person.getHobbies().stream().sorted()
                .map(hobbie -> getTableFormated(imgCell,hobbie))
                .collect(Collectors.toList()));
        }

       languagesAndHobbiesList.stream().map(item->new Cell().add(item).setBorder(Border.NO_BORDER)).forEach(finalTable::addCell);

        return finalTable;
    }

    private Table getTableFormated(Cell imgCell ,String libelle)
    {
        float[] innerTableColumnWidths = {1, 12};
        Table table = new Table(innerTableColumnWidths,true).setFontSize(PDFConstants.MEDIUM_FONT_SIZE).setBorder(Border.NO_BORDER);
        table.addCell(imgCell.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.RIGHT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(libelle)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.LEFT)
            .setBorder(Border.NO_BORDER));
        return table;
    }
}
