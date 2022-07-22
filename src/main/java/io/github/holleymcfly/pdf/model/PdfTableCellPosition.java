package io.github.holleymcfly.pdf.model;

public class PdfTableCellPosition {

    private int row;
    private int column;

    public PdfTableCellPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
