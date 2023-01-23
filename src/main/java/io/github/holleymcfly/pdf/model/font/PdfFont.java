package io.github.holleymcfly.pdf.model.font;

import io.github.holleymcfly.pdf.model.color.PdfColor;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PdfFont {

    private Standard14Fonts.FontName font;
    private int size;

    private PdfColor color;

    void setFont(Standard14Fonts.FontName font) {
        this.font = font;
    }

    void setSize(int size) {
        this.size = size;
    }

    void setColor(PdfColor color) {
        this.color = color;
    }

    public PDType1Font getFont() {
        return new PDType1Font(font);
    }

    public int getSize() {
        return size;
    }

    public PdfColor getColor() {
        return color;
    }
}
