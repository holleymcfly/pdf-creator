package io.github.holleymcfly.pdf.model.color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class PdfCmykColor implements PdfColor {

    private final float c;
    private final float m;
    private final float y;
    private final float k;

    PdfCmykColor(float c, float m, float y, float k) {
        this.c = c;
        this.m = m;
        this.y = y;
        this.k = k;
    }

    @Override
    public void setNonStrokingToContentStream(PDPageContentStream contentStream) throws IOException {
        contentStream.setNonStrokingColor(c, m, y, k);
    }
}
