package io.github.holleymcfly.pdf.core;

import io.github.holleymcfly.pdf.model.font.PdfFont;

public class PdfCreatorBuilder {

    private String headerText;
    private PdfFont headerFont;
    private String footerText;
    private PdfFont footerFont;

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

    public PdfCreator build() {

        PdfCreator pdfCreator = new PdfCreator();
        if (this.headerText != null && this.headerText.length() > 0) {
            pdfCreator.setHeaderText(this.headerText);
            pdfCreator.setHeaderFont(this.headerFont);
            pdfCreator.setPageTop(PdfCreator.PAGE_TOP_WITH_HEADER);
        }
        else {
            pdfCreator.setPageTop(PdfCreator.PAGE_TOP_WITHOUT_HEADER);
        }

        if (this.footerText != null && this.footerText.length() > 0) {
            pdfCreator.setFooterText(this.footerText);
            pdfCreator.setFooterFont(this.footerFont);
            pdfCreator.setPageBottom(PdfCreator.PAGE_BOTTOM_WITH_FOOTER);
        }
        else {
            pdfCreator.setPageBottom(PdfCreator.PAGE_BOTTOM_WITHOUT_FOOTER);
        }

        pdfCreator.init();
        return pdfCreator;
    }
}
