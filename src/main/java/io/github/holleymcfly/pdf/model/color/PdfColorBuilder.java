package io.github.holleymcfly.pdf.model.color;

import java.awt.*;

public class PdfColorBuilder {

    public static PdfColor createPdfColor(Color color) {
        return new PdfAwtColor(color);
    }

    public static PdfColor createPdfColor(int r, int g, int b) {
        return new PdfRgbColor(r, g, b);
    }

    public static PdfColor createPdfColor(float c, float m, float y, float k) {
        return new PdfCmykColor(c, m, y, k);
    }
}
