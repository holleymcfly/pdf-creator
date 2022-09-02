package io.github.holleymcfly.pdf.model.color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class PdfRgbColor implements PdfColor {

    private final float r;
    private final float g;
    private final float b;

    PdfRgbColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void setNonStrokingToContentStream(PDPageContentStream contentStream) throws IOException {
        contentStream.setNonStrokingColor(r, g, b);
    }
}
