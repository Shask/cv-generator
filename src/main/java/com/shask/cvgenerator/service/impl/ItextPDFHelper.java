package com.shask.cvgenerator.service.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.shask.cvgenerator.service.PDFHelper;
import org.springframework.stereotype.Service;

@Service
public class ItextPDFHelper implements PDFHelper {
    public static Cell newBasicCell(String content) {
        return new Cell().add(new Paragraph(content)).setBorder(Border.NO_BORDER);
    }

    public static Cell newBasicCell(String content, PdfFont font) {
        return newBasicCell(content).setFont(font);
    }

    public static Cell newBasicCell(String content, int colspan) {
        return newBasicCell(content, 1, colspan);
    }

    public static Cell newBasicCell(String content, int colspan, PdfFont font) {
        return newBasicCell(content, 1, colspan).setFont(font);
    }

    public static Cell newBasicCell(String content, int rowspan, int colspan, PdfFont font) {
        return newBasicCell(content, rowspan, colspan).setFont(font);
    }

    public static Cell newBasicCell(String content, int rowspan, int colspan) {
        return new Cell(rowspan, colspan).add(new Paragraph(content)).setBorder(Border.NO_BORDER);
    }
}
