package io.github.holleymcfly.pdf.model;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PdfFont {

    private PDType1Font font;
    private int size;

    public PdfFont() {
        this.font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
        this.size = 12;
    }

    public PdfFont(PDType1Font font) {
        this.font = font;
        this.size = 12;
    }

    public PdfFont(int size) {
        this.font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
        this.size = size;
    }

    public PdfFont(PDType1Font font, int size) {
        this.font = font;
        this.size = size;
    }

    public PDType1Font getFont() {
        return font;
    }

    public int getSize() {
        return size;
    }
}
