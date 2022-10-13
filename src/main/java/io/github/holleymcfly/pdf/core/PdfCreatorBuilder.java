package io.github.holleymcfly.pdf.core;

import io.github.holleymcfly.pdf.model.font.PdfFont;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PdfCreatorBuilder {

    private String headerText;
    private PdfFont headerFont;
    private String footerText;
    private PdfFont footerFont;
    private float pageMarginLeft = 25; // Set some suitable default.
    private float pageMarginRight = 50; // Set some suitable default.
    private Float pageMarginBottom = null;
    private float pageMarginTop = 30; // Set some suitable default.
    private PDRectangle pageFormat = PDRectangle.A4;

    public PdfCreatorBuilder withHeader(String headerText, PdfFont headerFont) {
        this.headerText = headerText;
        this.headerFont = headerFont;
        return this;
    }

    public PdfCreatorBuilder withFooter(String footerText, PdfFont footerFont) {
        this.footerText = footerText;
        this.footerFont = footerFont;
        return this;
    }

    public PdfCreatorBuilder withPageMarginLeft(float pageMarginLeft) {
        this.pageMarginLeft = pageMarginLeft;
        return this;
    }

    public PdfCreatorBuilder withPageMarginRight(float pageMarginRight) {
        this.pageMarginRight = pageMarginRight;
        return this;
    }

    public PdfCreatorBuilder withPageMarginBottom(float pageMarginBottom) {
        this.pageMarginBottom = pageMarginBottom;
        return this;
    }

    public PdfCreatorBuilder withPageMarginTop(float pageMarginTop) {
        this.pageMarginTop = pageMarginTop;
        return this;
    }

    public PdfCreatorBuilder withPageFormat(PDRectangle pageFormat) {
        this.pageFormat = pageFormat;
        return this;
    }

    public PdfCreator build() {

        PdfCreator pdfCreator = new PdfCreator();
        if (this.headerText != null && this.headerText.length() > 0) {
            pdfCreator.setHeaderText(this.headerText);
            pdfCreator.setHeaderFont(this.headerFont);
        }

        if (this.footerText != null && this.footerText.length() > 0) {
            pdfCreator.setFooterText(this.footerText);
            pdfCreator.setFooterFont(this.footerFont);

            if (this.pageMarginBottom == null) {
                pdfCreator.setPageMarginBottom(50); // Set a default.
            }
        }
        else {
            if (this.pageMarginBottom == null) {
                pdfCreator.setPageMarginBottom(30); // Set a default.
            }
        }

        if (this.pageMarginBottom != null) {
            pdfCreator.setPageMarginBottom(this.pageMarginBottom);
        }

        pdfCreator.setPageMarginTop(this.pageMarginTop);
        pdfCreator.setPageMarginLeft(this.pageMarginLeft);
        pdfCreator.setPageMarginRight(this.pageMarginRight);
        pdfCreator.setPageFormat(this.pageFormat);
        pdfCreator.init();
        return pdfCreator;
    }
}
