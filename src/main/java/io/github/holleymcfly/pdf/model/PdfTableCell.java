package io.github.holleymcfly.pdf.model;

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
    private PdfTableCellPosition position;
    private String content;

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

    public void init(int tableWidth, int numberOfColumns, PdfFont font) {

        if (this.font == null) {
            this.font = font;
        }

        calculateCellWidth(tableWidth, numberOfColumns);
        splitUpText();
        calculateCellHeight();
    }

    private void calculateCellWidth(int tableWidth, int numberOfColumns) {
        width = tableWidth / numberOfColumns;
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

    /**
     * Add all words to the line that match into the cell width.
     */
    private SplitInformation splitLine(String[] words) throws IOException {

        List<String> remainingWords = new LinkedList<>();

        String result = "";

        boolean first = true;
        StringBuilder lineTemp = new StringBuilder();
        for (String word : words) {

            if (!first) {
                lineTemp.append(" ");
            }
            first = false;

            lineTemp.append(word);

            float textWidth = font.getFont().getStringWidth(lineTemp.toString()) / 1000 * font.getSize();
            if (textWidth < contentWidth) {
                result = lineTemp.toString();
            }
            else {
                remainingWords.add(word);
            }
        }

        if ("".equals(result)) {
            // the next word didn't fit into the cell.
            // Split up the word forcefully.
            String wordToSplitUp = words[0];
            String remainingWord = "";
            float textWidth = 0f;
            for (int i=0; i<wordToSplitUp.length(); i++) {
                textWidth += font.getFont().getStringWidth(result) / 1000 * font.getSize();
                if (textWidth < contentWidth) {
                    result += wordToSplitUp.charAt(i);
                }
                else {
                    remainingWord += wordToSplitUp.charAt(i);
                }
            }

            remainingWords = new LinkedList<>();
            remainingWords.add(remainingWord);
        }

        String[] remaining = remainingWords.toArray(new String[0]);
        return new SplitInformation(remaining, result);
    }

    public void splitUpText() {

        List<String> lines = new LinkedList<>();

        try {
            String[] singleWords = content.split(" ");

            while (singleWords.length > 0) {
                SplitInformation si = splitLine(singleWords);
                lines.add(si.getLine());
                singleWords = si.getRemainingWords();
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not split up the text of the table cell.", e);
        }

        splitUpLines = lines.toArray(new String[0]);
    }

    private class SplitInformation {

        private String[] remainingWords;
        private String line;

        public SplitInformation(String[] remainingWords, String line) {
            this.remainingWords = remainingWords;
            this.line = line;
        }

        public String[] getRemainingWords() {
            return remainingWords;
        }

        public void setRemainingWords(String[] remainingWords) {
            this.remainingWords = remainingWords;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }
    }
}
