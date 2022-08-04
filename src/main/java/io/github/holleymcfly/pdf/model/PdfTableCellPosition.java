package io.github.holleymcfly.pdf.model;

public class PdfTableCellPosition {

    private final int row;
    private final int column;
    private int colspan = 1;

    public PdfTableCellPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public PdfTableCellPosition(int row, int column, int colspan) {
        this(row, column);
        this.colspan = colspan;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getColspan() {
        return colspan;
    }
}
