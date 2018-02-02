package com.shask.cvgenerator.service.impl.util;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;

import static com.shask.cvgenerator.util.PDFConstants.DOCUMENT_HEIGHT;
import static com.shask.cvgenerator.util.PDFConstants.DOCUMENT_MARGIN;
import static com.shask.cvgenerator.util.PDFConstants.DOCUMENT_WIDTH;

public class RhumbusLineSeparator extends DottedLine {

    @Override
    public void draw(PdfCanvas canvas, Rectangle drawArea) {

        final Float y = drawArea.getY();
        final Point center = new Point(DOCUMENT_WIDTH / 2, y);
        final Double width = DOCUMENT_HEIGHT * 0.005;

        canvas.saveState()
            .moveTo(center.x, center.y + width)
            .lineTo(center.x + width, center.y)
            .lineTo(center.x, center.y - width)
            .lineTo(center.x - width, center.y)
            .fillStroke()

            .moveTo(DOCUMENT_MARGIN, y)
            .lineTo(center.x - width * 2, y)
            .fill()

            .moveTo(center.x + width * 2, y)
            .lineTo(DOCUMENT_WIDTH - DOCUMENT_MARGIN, y)
            .fill()
            .restoreState();
    }
}


