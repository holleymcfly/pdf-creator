package io.github.holleymcfly.pdf.model.font;

import io.github.holleymcfly.pdf.model.color.PdfColor;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfFont {

    private PDType1Font font;
    private int size;

    private PdfColor color;

    void setFont(PDType1Font font) {
        this.font = font;
    }

    void setSize(int size) {
        this.size = size;
    }

    void setColor(PdfColor color) {
        this.color = color;
    }

    public PDType1Font getFont() {
        return font;
    }

    public int getSize() {
        return size;
    }

    public PdfColor getColor() {
        return color;
    }
}
