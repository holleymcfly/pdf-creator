package io.github.holleymcfly.pdf.model.color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public interface PdfColor {

    void setNonStrokingToContentStream(PDPageContentStream contentStream) throws IOException;
}
