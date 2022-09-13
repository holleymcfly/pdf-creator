package io.github.holleymcfly.pdf.model.table;

import io.github.holleymcfly.pdf.model.color.PdfColor;
import io.github.holleymcfly.pdf.model.font.PdfFont;

public class PdfTableCellBuilder {

    private PdfTableCellPosition position;
    private String content;
    private PdfFont font;
    private PdfColor backgroundColor;

    public PdfTableCellBuilder withPosition(PdfTableCellPosition position) {
        this.position = position;
        return this;
    }

    public PdfTableCellBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public PdfTableCellBuilder withFont(PdfFont font) {
        this.font = font;
        return this;
    }

    public PdfTableCellBuilder withBackgroundColor(PdfColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public PdfTableCell build() {

        PdfTableCell cell = new PdfTableCell(position, content);
        cell.setFont(font);
        cell.setBackgroundColor(backgroundColor);

        return cell;
    }
}
