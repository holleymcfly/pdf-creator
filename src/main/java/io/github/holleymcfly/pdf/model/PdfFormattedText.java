package io.github.holleymcfly.pdf.model;

import java.io.IOException;

/**
 * Encapsulates a text with a font assigned to it.
 */
public class PdfFormattedText {

    private String text;
    private PdfFont font;

    public PdfFormattedText(String text, PdfFont font) {
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public PdfFont getFont() {
        return font;
    }
}
