package io.github.holleymcfly.pdf.model.font;

import io.github.holleymcfly.pdf.model.color.PdfColor;
import io.github.holleymcfly.pdf.model.color.PdfColorBuilder;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.util.Objects;

public class PdfFontBuilder {

    private Standard14Fonts.FontName font;
    private int size = -1;

    private PdfColor color;

    public PdfFontBuilder withFont(Standard14Fonts.FontName font) {
        this.font = font;
        return this;
    }

    public PdfFontBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public PdfFontBuilder withColor(PdfColor color) {
        this.color = color;
        return this;
    }

    public PdfFont build() {
        PdfFont pdfFont = new PdfFont();
        pdfFont.setFont(Objects.requireNonNullElse(font, Standard14Fonts.FontName.TIMES_ROMAN));

        if (size != -1) {
            pdfFont.setSize(size);
        }
        else {
            pdfFont.setSize(12);
        }

        pdfFont.setColor(Objects.requireNonNullElseGet(color, () -> PdfColorBuilder.createPdfColor(Color.BLACK)));
        return pdfFont;
    }

}
