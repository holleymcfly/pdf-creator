package io.github.holleymcfly.pdf.core;

import io.github.holleymcfly.pdf.model.*;
import io.github.holleymcfly.pdf.util.TextHelper;
import io.github.holleymcfly.pdf.util.TextSplitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PdfCreator {

    public final static int DEFAULT_MARGIN_LEFT = 25;
    public final static int DEFAULT_MARGIN_RIGHT = 70;
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

    private int pageWidth;
    private int pageContentWidth;

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

        pageWidth = (int) currentPage.getMediaBox().getWidth();
        pageContentWidth = pageWidth - DEFAULT_MARGIN_LEFT - DEFAULT_MARGIN_RIGHT;

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

        int tableStartX = DEFAULT_MARGIN_LEFT;

        for (int i=0; i<table.getNumberOfRows(); i++) {

            List<PdfTableCell> cells = table.getCellsForRowOrdered(i+1);
            if (cells.size() == 0) {
                continue;
            }

            int rowHeight = table.getRowHeight(i+1);
            int rowWidth = tableStartX + table.getTableWidth();

            if (!fitsTableOnPage(rowHeight)) {
                newPage();
            }

            drawTableRowLines(tableStartX, table, cells, rowHeight, rowWidth);
            fillTableCellTexts(table, cells);

            currentY -= rowHeight;
        }
    }

    private boolean fitsTableOnPage(int height) {
        return (currentY - height) > pageBottom;
    }

    private void fillTableCellTexts(PdfTable table, List<PdfTableCell> cells) {

        int cellY = currentY;
        for (PdfTableCell cell : cells) {
            int x = table.getXofTableCell(cell, cell.getMarginLeft()) + DEFAULT_MARGIN_LEFT;
            addTextLines(cell.getSplitUpLines(), cell.getFont(), x, false, false, cellY);
        }

        // When setting the text to table cells, we don't modify the currentY value.
        // That's already handled in the calling method.
        currentY = cellY;
    }

    private void drawTableRowLines(int tableStartX, PdfTable table, List<PdfTableCell> cells, int rowHeight, int rowWidth) {

        drawTopHorizontalTableLine(tableStartX, rowWidth);
        drawLeftVerticalTableLine(tableStartX, rowHeight);
        drawRightVerticalTableLine(rowHeight, rowWidth);
        drawBottomHorizontalTableLine(tableStartX, rowHeight, rowWidth);
        drawVerticalRowLines(tableStartX, table, cells, rowHeight);
    }

    private void drawVerticalRowLines(int tableStartX, PdfTable table, List<PdfTableCell> cells, int rowHeight) {

        for (PdfTableCell cell : cells) {
            int x = table.getXofTableCell(cell, tableStartX);
            line(new PdfPoint(x, currentY), new PdfPoint(x, currentY - rowHeight));
        }
    }

    private void drawBottomHorizontalTableLine(int tableStartX, int rowHeight, int rowWidth) {
        line(new PdfPoint(tableStartX, currentY - rowHeight), new PdfPoint(rowWidth, currentY - rowHeight));
    }

    private void drawRightVerticalTableLine(int rowHeight, int rowWidth) {
        line(new PdfPoint(rowWidth, currentY), new PdfPoint(rowWidth, currentY - rowHeight));
    }

    private void drawLeftVerticalTableLine(int tableStartX, int rowHeight) {
        line(new PdfPoint(tableStartX, currentY), new PdfPoint(tableStartX, currentY - rowHeight));
    }

    private void drawTopHorizontalTableLine(int tableStartX, int rowWidth) {
        line(new PdfPoint(tableStartX, currentY), new PdfPoint(rowWidth, currentY));
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

    public void addTextLeftAligned(LinkedList<PdfFormattedText> formattedTexts) {
        addText(formattedTexts, DEFAULT_MARGIN_LEFT, false, false);
    }

    public void addTextCentered(LinkedList<PdfFormattedText> formattedTexts) {
        addText(formattedTexts, -1, true, false);
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
        String[] lines = new TextSplitter(new PdfFormattedText(text, font), pageContentWidth).splitUpText();
        addTextLines(lines, font, x, centered, ignoreBottom, currentY);
    }

    private void addText(String text, PdfFont font, int x, boolean centered, boolean ignoreBottom, int y) {
        String[] lines = new TextSplitter(new PdfFormattedText(text, font), pageContentWidth).splitUpText();
        addTextLines(lines, font, x, centered, ignoreBottom, y);
    }

    private void addText(LinkedList<PdfFormattedText> formattedTexts, int x, boolean centered, boolean ignoreBottom) {
        LinkedList<LinkedList<PdfFormattedText>> lines = new TextSplitter(formattedTexts, pageContentWidth)
                .splitUpTextWithWords();
        addTextLines(lines, x, centered, ignoreBottom, currentY);
    }

    private void addTextLines(LinkedList<LinkedList<PdfFormattedText>> textLines, int x, boolean centered,
            boolean ignoreBottom, int y) {

        try {
            PDPageContentStream contentStream = null;

            for (LinkedList<PdfFormattedText> line : textLines) {

                int textHeight = (int) TextHelper.getLineHeight(line);

                y = y - textHeight;
                if (y <= pageBottom && !ignoreBottom) {
                    newPage();
                    y = pageTop;
                }

                if (centered) {
                    float textWidth = TextHelper.getTotalWidth(line);
                    x = (int) (currentPage.getMediaBox().getWidth() - textWidth) / 2;
                }

                boolean first = true;

                contentStream = newContentStream();
                contentStream.beginText();

                for (PdfFormattedText word : line) {
                    if (first) {
                        contentStream.newLineAtOffset(x, y);
                        first = false;
                    }
                    contentStream.setFont(word.getFont().getFont(), word.getFont().getSize());
                    contentStream.showText(word.getText());
                }
                contentStream.endText();
                contentStream.close();
            }

        }
        catch (IOException e) {
            throw createRuntimeException(e, "Failed to add text to the page.");
        }

        currentY = y;
    }

    private void addTextLines(String[] textLines, PdfFont font, int x, boolean centered, boolean ignoreBottom, int y) {

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
                    float textWidth = TextHelper.getTextWidth(line, font);
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
