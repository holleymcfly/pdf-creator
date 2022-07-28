package io.github.holleymcfly.pdf.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PdfTable {

    private Set<PdfTableCell> cells = new HashSet<>();
    private PdfFont font;
    private int tableWidth;
    private int numberOfColumns;
    private LinkedList<Integer> columnWidths = new LinkedList<>();

    // Calculated when the table is initialized.
    private int numberOfRows;

    public PdfTable(PdfFont font, int tableWidth, int numberOfColumns) {
        this.font = font;
        this.tableWidth = tableWidth;
        this.numberOfColumns = numberOfColumns;

        initColumnWidths();
    }

    public PdfTable(PdfFont font, LinkedList<Integer> columnWidths) {
    	this.font = font;
    	this.numberOfColumns = columnWidths.size();
    	this.columnWidths.addAll(columnWidths);
    	this.tableWidth = calculateCompleteWidth();
    }
    
    private Integer calculateCompleteWidth() {
    	
    	int w = 0;
    	for (Integer columnWidth : columnWidths) {
    		w += columnWidth;
    	}
    	
    	return w;
    }
    
    private void initColumnWidths() {
    	
        for (int i=0; i<numberOfColumns; i++) {
        	int columnWidth = tableWidth / numberOfColumns;
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
            cell.init(tableWidth, numberOfColumns, font, columnWidths);
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

    public int getRowHeight(int row) {

        int maxHeight = 0;
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

        Collections.sort(cellsForRow, (o1, o2) -> {
            if (o1.getPosition().getColumn() > o2.getPosition().getColumn()) {
                return 1;
            }
            else if (o1.getPosition().getColumn() == o2.getPosition().getColumn()) {
                return 0;
            }
            else {
                return -1;
            }
        });

        return cellsForRow;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public PdfFont getFont() {
        return font;
    }

    public int getTableWidth() {
        return tableWidth;
    }

	public LinkedList<Integer> getColumnWidths() {
		return columnWidths;
	}
}
