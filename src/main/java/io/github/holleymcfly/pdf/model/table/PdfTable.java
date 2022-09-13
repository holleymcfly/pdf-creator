package io.github.holleymcfly.pdf.model.table;

import io.github.holleymcfly.pdf.model.font.PdfFont;

import java.util.*;

public class PdfTable {

    private final Set<PdfTableCell> cells = new HashSet<>();
    private final PdfFont font;
    private final float tableWidth;
    private final int numberOfColumns;
    private final LinkedList<Float> columnWidths = new LinkedList<>();

    // Calculated when the table is initialized.
    private int numberOfRows;

    public PdfTable(PdfFont font, float tableWidth, int numberOfColumns) {
        this.font = font;
        this.tableWidth = tableWidth;
        this.numberOfColumns = numberOfColumns;

        initColumnWidths();
    }

    public PdfTable(PdfFont font, LinkedList<Float> columnWidths) {
    	this.font = font;
    	this.numberOfColumns = columnWidths.size();
    	this.columnWidths.addAll(columnWidths);
    	this.tableWidth = calculateCompleteWidth();
    }
    
    private float calculateCompleteWidth() {
    	
    	float w = 0;
    	for (Float columnWidth : columnWidths) {
    		w += columnWidth;
    	}
    	
    	return w;
    }
    
    private void initColumnWidths() {
    	
        for (int i=0; i<numberOfColumns; i++) {
        	float columnWidth = tableWidth / numberOfColumns;
        	columnWidths.add(columnWidth);
        }	
    }
    
    public void addCell(PdfTableCell cell) {
        cells.add(cell);
    }

    /**
     * <b>Initializes each table cell:</b><br>
     * <ul>
     *     <li>Sets the font to the cell if it doesn't have a specific one.</li>
     *     <li>Calculates the width of the cell depending on the number of columns.</li>
     *     <li>Calculates the height of the cell depending on the column width, font and containing text.</li>
     * </ul>
     *
     */
    public void init() {
        for (PdfTableCell cell : cells) {
            cell.init(font, columnWidths);
        }

        calculateNumberOfRows();
    }

    private void calculateNumberOfRows() {

        for (PdfTableCell cell : cells) {
            if (cell.getPosition().getRow() > numberOfRows) {
                numberOfRows = cell.getPosition().getRow();
            }
        }
    }

    public float getRowHeight(int row) {

        float maxHeight = 0;
        for (PdfTableCell cell : getCellsForRowOrdered(row)) {
            if (cell.getHeight() > maxHeight) {
                maxHeight = cell.getHeight();
            }
        }

        return maxHeight;
    }

    /**
     * <b>Returns all cells for a single table row, ordered by the column index.</b><br>
     * <br>
     * @param row   The number of the row for which the cells shall be returned (starting at 1).
     * @return      The linked list of all cells in the requested row.
     */
    public LinkedList<PdfTableCell> getCellsForRowOrdered(int row) {

        LinkedList<PdfTableCell> cellsForRow = new LinkedList<>();

        for (PdfTableCell cell : cells) {
            if (cell.getPosition().getRow() == row) {
                cellsForRow.add(cell);
            }
        }

        cellsForRow.sort(Comparator.comparingInt(o -> o.getPosition().getColumn()));
        return cellsForRow;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public PdfFont getFont() {
        return font;
    }

    public float getTableWidth() {
        return tableWidth;
    }

    /**
     * <b>Returns the x position of the table cell with the given coordinates.</b><br>
     * <br>
     * If there is no cell at that position, -1 is returned.<br>
     *
     * @param row    The row of the cell the position shall be returned.
     * @param column The column of the cell the position shall be returned.
     * @param offset Some offset that will be added to the x position.
     * @return  The x value of the cell, including the offset. -1 if there is no cell at that position.
     */
    public float getXofTableCell(int row, int column, float offset) {

        LinkedList<PdfTableCell> cellsOfRow = getCellsForRowOrdered(row);
        float x = 0;
        for (PdfTableCell cell : cellsOfRow) {
            if (cell.getPosition().getRow() == row && cell.getPosition().getColumn() == column) {
                return x + offset;
            }

            x += cell.getWidth();
        }

        return -1;
    }

    /**
     * <b>Returns the x position of the table cell with the given coordinates.</b><br>
     * <br>
     * Same as <code>getXofTableCell(int row, int column, float offset)</code>, with a cell as input.<br>
     *
     * @param cell    The cell the position is taken from.
     * @param offset  Some offset that will be added to the x position.
     * @return  The x value of the cell, including the offset. -1 if there is no cell at that position.
     */
    public float getXofTableCell(PdfTableCell cell, float offset) {
        return getXofTableCell(cell.getPosition().getRow(), cell.getPosition().getColumn(), offset);
    }
}
