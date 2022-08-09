package io.github.holleymcfly.pdf.util;

import io.github.holleymcfly.pdf.model.PdfFont;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TextSplitter {

    private String text;
    private PdfFont font;
    private int lineWidth;

    public TextSplitter(String text, PdfFont font, int lineWidth) {
        this.text = text;
        this.font = font;
        this.lineWidth = lineWidth;
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
            if (textWidth < lineWidth) {
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
            StringBuilder remainingWord = new StringBuilder("");
            float textWidth = 0f;
            for (int i=0; i<wordToSplitUp.length(); i++) {
                textWidth += font.getFont().getStringWidth(result) / 1000 * font.getSize();
                if (textWidth < lineWidth) {
                    result += wordToSplitUp.charAt(i);
                }
                else {
                    remainingWord.append(wordToSplitUp.charAt(i));
                }
            }

            remainingWords = new LinkedList<>();
            remainingWords.add(remainingWord.toString());
        }

        String[] remaining = remainingWords.toArray(new String[0]);
        return new SplitInformation(remaining, result);
    }

    private static class SplitInformation {

        private final String[] remainingWords;
        private final String line;

        public SplitInformation(String[] remainingWords, String line) {
            this.remainingWords = remainingWords;
            this.line = line;
        }

        public String[] getRemainingWords() {
            return remainingWords;
        }

        public String getLine() {
            return line;
        }
    }

    /**
     * <b>Splits up the text to fit into the line.</b>
     * @return An array with the single lines.
     */
    public String[] splitUpText() {

        if (text == null || text.equals("")) {
            // One empty string leads to a new line.
            String[] result = new String[1];
            result[0] = "";
            return result;
        }

        List<String> lines = new LinkedList<>();

        try {
            String[] singleWords = text.split(" ");

            while (singleWords.length > 0) {
                SplitInformation si = splitLine(singleWords);
                lines.add(si.getLine());
                singleWords = si.getRemainingWords();
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not split up the text of the table cell.", e);
        }

        return lines.toArray(new String[0]);
    }
}