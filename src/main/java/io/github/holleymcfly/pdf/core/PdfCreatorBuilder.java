package io.github.holleymcfly.pdf.core;

import io.github.holleymcfly.pdf.model.font.PdfFont;

public class PdfCreatorBuilder {

    private String headerText;
    private PdfFont headerFont;
    private String footerText;
    private PdfFont footerFont;
    private float pageMarginLeft = 25; // Set some suitable default.
    private float pageMarginRight = 50; // Set some suitable default.

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

    public PdfCreator build() {

        PdfCreator pdfCreator = new PdfCreator();
        if (this.headerText != null && this.headerText.length() > 0) {
            pdfCreator.setHeaderText(this.headerText);
            pdfCreator.setHeaderFont(this.headerFont);
        }

        if (this.footerText != null && this.footerText.length() > 0) {
            pdfCreator.setFooterText(this.footerText);
            pdfCreator.setFooterFont(this.footerFont);
            pdfCreator.setPageBottom(PdfCreator.PAGE_BOTTOM_WITH_FOOTER);
        }
        else {
            pdfCreator.setPageBottom(PdfCreator.PAGE_BOTTOM_WITHOUT_FOOTER);
        }

        pdfCreator.setPageMarginLeft(this.pageMarginLeft);
        pdfCreator.setPageMarginRight(this.pageMarginRight);
        pdfCreator.init();
        return pdfCreator;
    }
}
