package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.shask.cvgenerator.model.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;
import com.shask.cvgenerator.util.PDFConstants;

public class ShortOverviewGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {
        return new Paragraph(person.getShortPresentation()).setFont(PDFConstants.FONT_HELVETIVA_NUEUE).setFontSize(8);
    }
}
