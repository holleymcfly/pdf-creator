package io.github.holleymcfly.pdf.model.color;

import java.awt.*;

public class PdfColorBuilder {

    public static PdfColor createPdfColor(Color color) {
        return new PdfAwtColor(color);
    }

    // Setting colors in RGB and CMYK does not seem to work properly in pdfbox.
    // See https://issues.apache.org/jira/browse/PDFBOX-5498

//    public static PdfColor createPdfColor(float r, float g, float b) {
//        return new PdfRgbColor(r, g, b);
//    }

    public static PdfColor createPdfColor(float c, float m, float y, float k) {
        return new PdfCmykColor(c, m, y, k);
    }
}
