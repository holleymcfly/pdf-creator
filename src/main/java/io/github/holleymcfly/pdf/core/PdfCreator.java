package io.github.holleymcfly.pdf.core;

import io.github.holleymcfly.pdf.model.PdfFont;
import io.github.holleymcfly.pdf.model.PdfPoint;
import io.github.holleymcfly.pdf.model.PdfTable;
import io.github.holleymcfly.pdf.model.PdfTableCell;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.List;

public class PdfCreator {

    public final static int DEFAULT_MARGIN_LEFT = 25;
    public final static int DEFAULT_PAGE_END = 585;
    public final static int PAGE_BOTTOM_WITHOUT_FOOTER = 30;
    public final static int PAGE_BOTTOM_WITH_FOOTER = 50;
    public final static int PAGE_TOP_WITHOUT_HEADER = 750;
    public final static int PAGE_TOP_WITH_HEADER = 730;

    private final PDDocument document;
    private PDPage currentPage;
    private int currentY = 0;


    private int pageTop;
    private int pageBottom;

    private String headerText;
    private PdfFont headerFont;
    private String footerText;
    private PdfFont footerFont;

    protected PdfCreator() {
        this.document = new PDDocument();
    }

    protected void init() {
        newPage();
    }

    /**
     * <b>Inserts a new page into the pdf document.</b>
     */
    public void newPage() {
        PDPage page = new PDPage();
        document.addPage(page);
        currentPage = page;

        addHeaderToPage();
        addFooterToPage();

        currentY = pageTop;
    }

    private void addHeaderToPage() {
        if (headerText != null && headerText.length() > 0) {
            addText(headerText, headerFont, DEFAULT_MARGIN_LEFT, false, false, 780);
        }
        line(new PdfPoint(DEFAULT_MARGIN_LEFT, 760), new PdfPoint(DEFAULT_PAGE_END, 760));
    }

    private void addFooterToPage() {
        if (footerText != null && footerText.length() > 0) {
            addText(footerText, footerFont, DEFAULT_MARGIN_LEFT, false, true, 35);
        }
        line(new PdfPoint(DEFAULT_MARGIN_LEFT, 40), new PdfPoint(DEFAULT_PAGE_END, 40));
    }

    /**
     * <b>Draws a line from the point <code>from</code> to the point <code>to</code>.</b><br>
     * <br>
     * @param from  The starting point for the line.
     * @param to    The end point of the line.
     */
    public void line(PdfPoint from, PdfPoint to) {

        try {
            PDPageContentStream contentStream = newContentStream();
            contentStream.moveTo(from.getX(), from.getY());
            contentStream.lineTo(to.getX(), to.getY());
            contentStream.stroke();
            contentStream.close();
        }
        catch (IOException e) {
            throw createRuntimeException(e, "Failed to create the header line.");
        }
    }

    /**
     * <b>Creates the given table.</b><br>
     * <br>
     * The given table has to be fully configured, i.e. it already includes all columns and content.
     * Changing the table later is not possible.<br>
     * <br>
     * @param table     The fully configured table to print
     */
    public void addTable(PdfTable table) {

        currentY -= table.getFont().getSize();

        table.init();

        int rowX = DEFAULT_MARGIN_LEFT;

        for (int i=0; i<table.getNumberOfRows(); i++) {

            List<PdfTableCell> cells = table.getCellsForRowOrdered(i+1);
            if (cells.size() == 0) {
                continue;
            }

            int rowHeight = table.getRowHeight(i+1);
            int rowWidth = rowX + table.getTableWidth();

            if (!fitsOnPage(rowHeight)) {
                newPage();
            }

            drawTableRowLines(rowX, table, cells, rowHeight, rowWidth);
            fillTableCellTexts(rowX, cells);

            currentY -= rowHeight;
        }
    }

    private boolean fitsOnPage(int height) {
        return (currentY - height) > pageBottom;
    }

    private void fillTableCellTexts(int rowX, List<PdfTableCell> cells) {

        int cellX = rowX;
        int cellY = currentY;
        for (PdfTableCell cell : cells) {
            cellX += cell.getMarginLeft();
            addText(cell.getSplitUpLines(), cell.getFont(), cellX, false, false, cellY);
            cellX += cell.getContentWidth() + cell.getMarginRight();
        }

        // When setting the text to table cells, we don't modify the currentY value.
        // That's already handled in the calling method.
        currentY = cellY;
    }

    private void drawTableRowLines(int rowX, PdfTable table, List<PdfTableCell> cells, int rowHeight, int rowWidth) {

        // top line
        line(new PdfPoint(rowX, currentY), new PdfPoint(rowWidth, currentY));
        // left vertical line
        line(new PdfPoint(rowX, currentY), new PdfPoint(rowX, currentY - rowHeight));
        // right vertical line
        line(new PdfPoint(rowWidth, currentY), new PdfPoint(rowWidth, currentY - rowHeight));
        // bottom line
        line(new PdfPoint(rowX, currentY - rowHeight), new PdfPoint(rowWidth, currentY - rowHeight));
        // lines between the cells
        int cellX = rowX;
        for (int j=0; j<table.getColumnWidths().size(); j++) {
        	cellX += table.getColumnWidths().get(j);
            line(new PdfPoint(cellX, currentY), new PdfPoint(cellX, currentY - rowHeight));
        }
    }

    public void setPageTop(int pageTop) {
        this.pageTop = pageTop;
    }

    public void setPageBottom(int pageBottom) {
        this.pageBottom = pageBottom;
    }

    /**
     * <b>Adds the given text at the given position to the current page.</b><br>
     * <br>
     * The text always starts on a new line. It The text will be wrapped automatically.
     * If the text doesn't fit on the page, a new page will be created.<br>
     * <br>
     * @param text  The text to add
     * @param font  The font to use for printing the text
     */
    public void addTextLeftAligned(String text, PdfFont font) {
        addText(text, font, DEFAULT_MARGIN_LEFT, false, false);
    }

    public void addNewLine(PdfFont font) {
        addText("", font, 0, false, false);
    }

    /**
     * <b>Adds the given text center aligned to the current page.</b><br>
     * <br>
     * The text always starts on a new line. It The text will be wrapped automatically.
     * If the text doesn't fit on the page, a new page will be created.<br>
     * <br>
     * @param text  The text to add
     * @param font  The font to use for printing the text
     */
    public void addTextCentered(String text, PdfFont font) {
        addText(text, font, -1, true, false);
    }

    private void addText(String text, PdfFont font, int x, boolean centered, boolean ignoreBottom) {
        String[] wrT = WordUtils.wrap(text, 110).split("\\r?\\n");
        addText(wrT, font, x, centered, ignoreBottom, currentY);
    }

    private void addText(String text, PdfFont font, int x, boolean centered, boolean ignoreBottom, int y) {
        String[] wrT = WordUtils.wrap(text, 110).split("\\r?\\n");
        addText(wrT, font, x, centered, ignoreBottom, y);
    }

    private void addText(String[] textLines, PdfFont font, int x, boolean centered, boolean ignoreBottom, int y) {

        try {
            PDPageContentStream contentStream = newContentStream();

            int textHeight = (int) font.getFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font.getSize();

            for (String line : textLines) {
                y = y - textHeight;
                if (y <= pageBottom && !ignoreBottom) {
                    contentStream.close();
                    newPage();
                    contentStream = newContentStream();
                    y = pageTop;
                }

                if (centered) {
                    float textWidth = font.getFont().getStringWidth(line) / 1000 * font.getSize();
                    x = (int) (currentPage.getMediaBox().getWidth() - textWidth) / 2;
                }

                contentStream.beginText();
                contentStream.setFont(font.getFont(), font.getSize());
                contentStream.newLineAtOffset(x, y);

                contentStream.showText(line);
                contentStream.endText();
            }

            contentStream.close();

        }
        catch (IOException e) {
            throw createRuntimeException(e, "Failed to add text to the page.");
        }

        currentY = y;
    }

    private PDPageContentStream newContentStream() {
        try {
            return new PDPageContentStream(document, currentPage, PDPageContentStream.AppendMode.APPEND, true);
        }
        catch (IOException e) {
            throw createRuntimeException(e, "Failed to save the current document.");
        }
    }

    /**
     * <b>Saves the current document to the given output path.</b><br>
     * <br>
     * @param path  The path to the local file system (including the file name) where the document shall be saved.
     */
    public void save(String path) {
        try {
            document.save(path);
        }
        catch (IOException e) {
            throw createRuntimeException(e, "Failed to save the current document.");
        }
    }

    /**
     * <b>Closes the given document.</b>
     */
    public void closeDocument() {

        try {
            document.close();
        }
        catch (Exception e) {
            throw createRuntimeException(e, "Failed to close the given document.");
        }
    }

    protected void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    protected void setHeaderFont(PdfFont headerFont) {
        this.headerFont = headerFont;
    }

    protected  void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    protected void setFooterFont(PdfFont footerFont) {
        this.footerFont = footerFont;
    }

    private RuntimeException createRuntimeException(Exception e, String message) {
        throw new RuntimeException(message, e);
    }
}
