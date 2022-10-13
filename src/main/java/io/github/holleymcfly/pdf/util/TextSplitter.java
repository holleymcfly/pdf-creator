package io.github.holleymcfly.pdf.util;

import io.github.holleymcfly.pdf.model.PdfFormattedText;

import java.util.LinkedList;

public class TextSplitter {

    private final LinkedList<PdfFormattedText> formattedText;
    private LinkedList<PdfFormattedText> formattedWords;
    private final float lineWidth;

    public TextSplitter(PdfFormattedText f, float lineWidth) {

        this.formattedText = new LinkedList<>();
        this.formattedText.add(f);
        this.lineWidth = lineWidth;

        convertToFormattedWords();
    }

    public TextSplitter(LinkedList<PdfFormattedText> formattedText, float lineWidth) {
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

    public LinkedList<LinkedList<PdfFormattedText>> splitUpTextWithWords() {

        LinkedList<LinkedList<PdfFormattedText>> result = new LinkedList<>();

        float textWidth = 0;
        LinkedList<PdfFormattedText> wordsInALine = new LinkedList<>();
        for (PdfFormattedText formattedWord : formattedWords) {

            textWidth += TextHelper.getTextWidth(formattedWord.getText(), formattedWord.getFont());
            textWidth += TextHelper.getTextWidth(" ", formattedWord.getFont());
            if (textWidth < lineWidth) {
                if (wordsInALine.size() > 0) {
                    wordsInALine.add(new PdfFormattedText(" " + formattedWord.getText(), formattedWord.getFont()));
                }
                else {
                    wordsInALine.add(new PdfFormattedText(formattedWord.getText(), formattedWord.getFont()));
                }
            }
            else {
                result.add(new LinkedList<>(wordsInALine));
                wordsInALine.clear();
                wordsInALine.add(new PdfFormattedText(formattedWord.getText(), formattedWord.getFont()));
                textWidth = TextHelper.getTextWidth(formattedWord.getText(), formattedWord.getFont());
            }
        }

        result.add(wordsInALine);
        return result;
    }

    public String[] splitUpText() {

        LinkedList<String> result = new LinkedList<>();

        float textWidth = 0;
        StringBuilder line = new StringBuilder();
        for (PdfFormattedText formattedWord : formattedWords) {

            textWidth += TextHelper.getTextWidth(formattedWord.getText(), formattedWord.getFont());
            textWidth += TextHelper.getTextWidth(" ", formattedWord.getFont());
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
                textWidth = TextHelper.getTextWidth(formattedWord.getText(), formattedWord.getFont());
            }
        }

        result.add(line.toString());
        return result.toArray(new String[0]);
    }
}
