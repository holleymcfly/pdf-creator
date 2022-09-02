package io.github.holleymcfly.pdf.model.color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class PdfRgbColor implements PdfColor {

    private final float r;
    private final float g;
    private final float b;

    PdfRgbColor(int r, int g, int b) {
        this.r = Float.parseFloat(r+"") / 255;
        this.g = Float.parseFloat(g+"") / 255;
        this.b = Float.parseFloat(b+"") / 255;
    }

    @Override
    public void setNonStrokingToContentStream(PDPageContentStream contentStream) throws IOException {
        contentStream.setNonStrokingColor(r, g, b);
    }
}
