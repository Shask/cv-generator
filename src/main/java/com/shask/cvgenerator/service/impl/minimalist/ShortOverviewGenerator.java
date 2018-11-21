package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.util.PDFConstants;

import java.util.Objects;

public class ShortOverviewGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(person.getShortPresentation());
        return new Paragraph(person.getShortPresentation()).setFont(PDFConstants.FONT_HELVETIVA_NUEUE.get()).setFontSize(PDFConstants.MEDIUM_FONT_SIZE);
    }
}
