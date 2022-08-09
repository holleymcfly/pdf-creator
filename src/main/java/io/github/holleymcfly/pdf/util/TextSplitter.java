package io.github.holleymcfly.pdf.util;

import io.github.holleymcfly.pdf.model.PdfFont;
import io.github.holleymcfly.pdf.model.PdfFormattedText;

import java.io.IOException;
import java.util.LinkedList;

public class TextSplitter {

    private LinkedList<PdfFormattedText> formattedText;
    private LinkedList<PdfFormattedText> formattedWords;
    private int lineWidth;

    public TextSplitter(PdfFormattedText f, int lineWidth) {

        this.formattedText = new LinkedList<>();
        this.formattedText.add(f);
        this.lineWidth = lineWidth;

        convertToFormattedWords();
    }

    public TextSplitter(LinkedList<PdfFormattedText> formattedText, int lineWidth) {
        this.formattedText = new LinkedList<>(formattedText);
        this.lineWidth = lineWidth;

        convertToFormattedWords();
    }

    /**
     * Splits up the text information (some text with its fonts) into single word information (single words with
     * its fonts).
     */
    private void convertToFormattedWords() {

        formattedWords = new LinkedList<>();

        for (PdfFormattedText text : formattedText) {

            String[] words = text.getText().split(" ");
            for (String word : words) {
                formattedWords.add(new PdfFormattedText(word, text.getFont()));
            }
        }
    }

    public String[] splitUpText() {

        LinkedList<String> result = new LinkedList<>();

        float textWidth = 0;
        StringBuilder line = new StringBuilder();
        for (PdfFormattedText formattedWord : formattedWords) {

            textWidth += getWidth(formattedWord.getText(), formattedWord.getFont());
            if (textWidth < lineWidth) {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(formattedWord.getText());
            }
            else {
                result.add(line.toString());
                line.setLength(0);
                line.append(formattedWord.getText());
                textWidth = getWidth(formattedWord.getText(), formattedWord.getFont());
            }
        }

        result.add(line.toString());
        return result.toArray(new String[0]);
    }

    /**
     * <b>Calculates the width of the given text.</b>
     * @return The text width.
     */
    public static float getWidth(String text, PdfFont font) {

        try {
            return font.getFont().getStringWidth(text) / 1000 * font.getSize();
        }
        catch (IOException e) {
            throw new RuntimeException("Could not calculate the width of " + text, e);
        }
    }
}
