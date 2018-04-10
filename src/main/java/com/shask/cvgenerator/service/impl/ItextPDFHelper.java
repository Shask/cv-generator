package com.shask.cvgenerator.service.impl;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import lombok.extern.java.Log;

import java.net.MalformedURLException;
import java.util.Optional;

@Log
public class ItextPDFHelper {


    public static Cell newEmptyCell() {
        return new Cell().setBorder(Border.NO_BORDER);
    }
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
        return new Cell(rowspan, colspan).add(new Paragraph(content == null ? "" : content)).setBorder(Border.NO_BORDER);
    }

    public static Optional<Image> loadImage(String imgUrl) {
        return loadImage(imgUrl, null);
    }

    public static Optional<Image> loadImage(String imgUrl, String fallbackUrl) {
        try {
            return Optional.of(new Image(ImageDataFactory.create(imgUrl)));
        } catch (IOException | NullPointerException | MalformedURLException e) {
            log.info("Couldn't find image with the given url : " + imgUrl);
            if (fallbackUrl != null) {
                try {
                    return Optional.of(new Image(ImageDataFactory.create(fallbackUrl)));
                } catch (MalformedURLException | IOException e1) {
                    log.severe("Couldn't find fallback image : " + fallbackUrl + " in filesystem");
                }
            }
        }
        return Optional.empty();
    }

    public static Cell newHyperLinkCenteredCell(String hyperlink, String label) {
        return new Cell().add(new Paragraph(label).setAction(PdfAction.createURI(hyperlink))
                .setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(Border.NO_BORDER))
            .setBorder(Border.NO_BORDER);
    }

    public static Cell newHyperLinkWithLogoCell(String logoUrl, String hyperlink, String label) {
        final float[] columnWidths = {1, 8};
        Table table = new Table(2, false).setBorder(Border.NO_BORDER).setMinHeight(16).setMinWidth(16);

        Optional<Image> logo = ItextPDFHelper.loadImage(logoUrl);
        if (logo.isPresent()) {
            Image img = logo.get().scaleToFit(10,10);
            table.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
        } else {
            table.addCell(new Cell().setBorder(Border.NO_BORDER));
        }

        Paragraph content = new Paragraph(label);
        if (hyperlink != null && ! hyperlink.trim().isEmpty()) {
            content = content.setAction(PdfAction.createURI(hyperlink)).setUnderline();
        }

        table.addCell(new Cell().add(content).setBorder(Border.NO_BORDER));
        return new Cell().add(table).setBorder(Border.NO_BORDER);
    }
}
