package io.github.holleymcfly.pdf.model;

import io.github.holleymcfly.pdf.util.TextSplitter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PdfTableCell {

    private final static int MARGIN_TOP = 3;
    private final static int MARGIN_BOTTOM = 3;
    private final static int MARGIN_LEFT = 3;
    private final static int MARGIN_RIGHT = 3;

    /**
     * The position of the cell within the table, consisting of the row and column.
     * Both starting at position 1.
     */
    private final PdfTableCellPosition position;
    private final String content;

    private PdfFont font;

    // The width of the cell, calculated with regarding the number of cells in a row.
    private int width;

    // The width of the cell content, i.e. where some text can be placed.
    private int contentWidth;

    // The height of the cell, calculated with regarding the text, the font and the cell width.
    private int height;

    // The text split up into single lines that fit into the cell.
    String[] splitUpLines;

    public PdfTableCell(PdfTableCellPosition position, String content) {
        this.position = position;
        this.content = content;
    }

    public PdfTableCell(PdfTableCellPosition position, String content, PdfFont font) {
        this.position = position;
        this.content = content;
        this.font = font;
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

    public void setFont(PdfFont font) {
        this.font = font;
    }

    public void init(PdfFont font, LinkedList<Integer> columnWidths) {

        if (this.font == null) {
            this.font = font;
        }

        calculateCellWidth(columnWidths);
        splitUpLines = new TextSplitter(new PdfFormattedText(content, font), contentWidth).splitUpText();
        calculateCellHeight();
    }

    private void calculateCellWidth(LinkedList<Integer> columnWidths) {

        for (int i=0; i<position.getColspan(); i++) {
            width += columnWidths.get(position.getColumn()-1+i);
        }
        contentWidth = width - MARGIN_LEFT - MARGIN_RIGHT;
    }

    private void calculateCellHeight() {
        int oneLineHeight = (int) font.getFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font.getSize();
        int numberOfLines = splitUpLines.length;

        height = MARGIN_TOP + (oneLineHeight * numberOfLines) + MARGIN_BOTTOM;
    }

    public int getWidth() {
        return width;
    }

    public int getContentWidth() {
        return contentWidth;
    }

    public int getHeight() {
        return height;
    }

    public String[] getSplitUpLines() {
        return splitUpLines;
    }

    public int getMarginLeft() {
        return MARGIN_LEFT;
    }

    public int getMarginRight() {
        return MARGIN_RIGHT;
    }

    public int getMarginTop() {
        return MARGIN_TOP;
    }

    public int getMarginBottom() {
        return MARGIN_BOTTOM;
    }
}
