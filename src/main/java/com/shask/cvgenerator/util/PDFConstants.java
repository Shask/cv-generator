package com.shask.cvgenerator.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class PDFConstants {
    public static final Float DOCUMENT_WIDTH = PageSize.A4.getWidth();
    public static final Float DOCUMENT_HEIGHT = PageSize.A4.getHeight();
    public static final Float DOCUMENT_MARGIN =  PageSize.A4.getWidth() * 0.01f;
    public static final String FONT = "./src/main/resources/font/HelveticaNeue.ttf";


    public static PdfFont FONT_HELVETIVA_NUEUE = null;
    public static PdfFont FONT_HELVETIVA = null;
    public static PdfFont FONT_HELVETIVA_BOLD = null;

    static {
        try {
            FONT_HELVETIVA_BOLD = PdfFontFactory.createFont("Helvetica-Bold");
            FONT_HELVETIVA =  PdfFontFactory.createFont("Helvetica");
            FONT_HELVETIVA_NUEUE = PdfFontFactory.createFont(FONT, true);
        } catch (IOException e) {
          log.severe("Couldn't create PDF fonts");
        }
    }



}