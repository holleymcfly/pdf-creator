package io.github.holleymcfly.pdf.util;

import io.github.holleymcfly.pdf.model.PdfFont;
import io.github.holleymcfly.pdf.model.PdfFormattedText;

import java.io.IOException;
import java.util.LinkedList;

public class TextHelper {

    /**
     * <b>Calculates the width of the given text and font.</b>
     * @param text The text for which the width shall be calculated.
     * @param font The font that is used for calculating the text width.
     * @return The text width.
     */
    public static float getTextWidth(String text, PdfFont font) {

        try {
            return font.getFont().getStringWidth(text) / 1000 * font.getSize();
        }
        catch (IOException e) {
            throw new RuntimeException("Could not calculate the width of " + text, e);
        }
    }

    /**
     * <b>Calculates the maximum height of the list of texts and corresponding fonts.</b>
     * @param words The list of words, including theirs fonts for calculating the line height.
     * @return The maximum text height.
     */
    public static float getLineHeight(LinkedList<PdfFormattedText> words) {

        float maxHeight = 0;

        for (PdfFormattedText word : words) {

            float height = word.getFont().getFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 *
                    word.getFont().getSize();
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }

    /**
     * <b>Calculates the width of the line, consisting of the given words.</b>
     * @param words The list of words, including their fonts for calculating the total width of the words.
     * @return The width of the line.
     */
    public static float getTotalWidth(LinkedList<PdfFormattedText> words) {

        float width = 0;
        for (PdfFormattedText word : words) {
            width += getTextWidth(word.getText(), word.getFont());
        }
        return width;
    }
}
