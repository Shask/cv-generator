package com.shask.cvgenerator.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.shask.cvgenerator.exception.FontNotFoundException;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.function.Supplier;

@Log
public class PDFConstants {
    public static final Float DOCUMENT_WIDTH = PageSize.A4.getWidth();
    public static final Float DOCUMENT_HEIGHT = PageSize.A4.getHeight();
    public static final Float DOCUMENT_MARGIN =  PageSize.A4.getWidth() * 0.02f;
    public static final String HELVETICA_NEUE_FONT_FILE = "./src/main/resources/font/HelveticaNeue.ttf";

    public static final String GITHUB_ICON = "./src/main/resources/github-logo.jpg";
    public static final String LINKEDIN_ICON = "./src/main/resources/linkedin-logo.jpg";
    public static final String EMAIL_ICON = "./src/main/resources/gmail-logo.jpg";
    public static final String RHOMBUS_ICON = "./src/main/resources/rhombusLD.png";

    public static Integer VERY_LARGE_FONT_SIZE = 20;
    public static Integer LARGE_FONT_SIZE = 14;
    public static Integer MEDIUM_FONT_SIZE = 10;
    public static Integer MEDIUM_PLUS_FONT_SIZE = 11;
    public static Integer SMALL_FONT_SIZE = 8;


    public static Supplier<PdfFont> FONT_HELVETIVA_BOLD = supplyFont("Helvetica-Bold",false);
    public static Supplier<PdfFont> FONT_HELVETIVA = supplyFont("Helvetica",false);
    public static Supplier<PdfFont> FONT_HELVETIVA_NUEUE = supplyFont(HELVETICA_NEUE_FONT_FILE,true);

    private static Supplier<PdfFont> supplyFont(String fontName, boolean embeded) {
       final PdfFont font;
        try {
            font = PdfFontFactory.createFont(fontName,embeded);
        }catch (IOException e) {
            log.severe("Couldn't create PDF fonts");
            throw new FontNotFoundException();
        }
        return () -> font;
    }


}
