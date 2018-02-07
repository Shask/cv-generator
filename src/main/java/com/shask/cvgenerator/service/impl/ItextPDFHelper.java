package com.shask.cvgenerator.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.shask.cvgenerator.service.PDFHelper;
import lombok.extern.java.Log;

import java.net.MalformedURLException;
import java.util.Optional;

@Log
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

    public static Optional<Image> loadImage(String imgUrl) {
        return loadImage(imgUrl, null);
    }

    public static Optional<Image> loadImage(String imgUrl, String fallbackUrl) {

        try {
            return Optional.of(new Image(ImageDataFactory.create(imgUrl)));
        } catch (MalformedURLException e) {
            log.warning("Couldn't find image with the given url");
            if (fallbackUrl != null) {
                try {
                    return Optional.of(new Image(ImageDataFactory.create(fallbackUrl)));
                } catch (MalformedURLException e1) {
                    log.severe("Couldn't find image in filesystem");
                }
            }
        }
        return Optional.empty();
    }
}