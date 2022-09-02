package io.github.holleymcfly.pdf.model;

import io.github.holleymcfly.pdf.model.font.PdfFont;

/**
 * Encapsulates a text with a font assigned to it.
 */
public class PdfFormattedText {

    private final String text;
    private final PdfFont font;

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
