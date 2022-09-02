package io.github.holleymcfly.pdf.model.color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.*;
import java.io.IOException;

public class PdfAwtColor implements PdfColor {

    private final Color color;

    PdfAwtColor(Color color) {
        this.color = color;
    }

    @Override
    public void setNonStrokingToContentStream(PDPageContentStream contentStream) throws IOException {
        contentStream.setNonStrokingColor(color);
    }
}
