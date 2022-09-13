package io.github.holleymcfly.pdf.model.table;

import io.github.holleymcfly.pdf.model.color.PdfColor;
import io.github.holleymcfly.pdf.model.font.PdfFont;
import io.github.holleymcfly.pdf.model.PdfFormattedText;
import io.github.holleymcfly.pdf.util.TextSplitter;

import java.util.LinkedList;

public class PdfTableCell {

    private final static float MARGIN_TOP = 3;
    private final static float MARGIN_BOTTOM = 3;
    private final static float MARGIN_LEFT = 3;
    private final static float MARGIN_RIGHT = 3;

    /**
     * The position of the cell within the table, consisting of the row and column.
     * Both starting at position 1.
     */
    private final PdfTableCellPosition position;
    private final String content;

    private PdfFont font;

    private PdfColor backgroundColor;

    // The width of the cell, calculated with regarding the number of cells in a row.
    private float width;

    // The width of the cell content, i.e. where some text can be placed.
    private float contentWidth;

    // The height of the cell, calculated with regarding the text, the font and the cell width.
    private float height;

    // The text split up into single lines that fit into the cell.
    String[] splitUpLines;

    PdfTableCell(PdfTableCellPosition position, String content) {
        this.position = position;
        this.content = content;
    }

    public PdfTableCellPosition getPosition() {
        return position;
    }

    public String getContent() {
        return content;
    }

    public PdfFont getFont() {
        return font;
    }

    void setFont(PdfFont font) {
        this.font = font;
    }

    public PdfColor getBackgroundColor() {
        return backgroundColor;
    }

    void setBackgroundColor(PdfColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void init(PdfFont font, LinkedList<Float> columnWidths) {

        if (this.font == null) {
            this.font = font;
        }

        calculateCellWidth(columnWidths);
        splitUpLines = new TextSplitter(new PdfFormattedText(content, font), contentWidth).splitUpText();
        calculateCellHeight();
    }

    private void calculateCellWidth(LinkedList<Float> columnWidths) {

        for (int i=0; i<position.getColspan(); i++) {
            width += columnWidths.get(position.getColumn()-1+i);
        }
        contentWidth = width - MARGIN_LEFT - MARGIN_RIGHT;
    }

    private void calculateCellHeight() {
        float oneLineHeight = font.getFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font.getSize();
        int numberOfLines = splitUpLines.length;

        height = MARGIN_TOP + (oneLineHeight * numberOfLines) + MARGIN_BOTTOM;
    }

    public float getWidth() {
        return width;
    }

    public float getContentWidth() {
        return contentWidth;
    }

    public float getHeight() {
        return height;
    }

    public String[] getSplitUpLines() {
        return splitUpLines;
    }

    public float getMarginLeft() {
        return MARGIN_LEFT;
    }

    public float getMarginRight() {
        return MARGIN_RIGHT;
    }

    public float getMarginTop() {
        return MARGIN_TOP;
    }

    public float getMarginBottom() {
        return MARGIN_BOTTOM;
    }
}
