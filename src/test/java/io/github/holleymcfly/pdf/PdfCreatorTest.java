package io.github.holleymcfly.pdf;

import io.github.holleymcfly.pdf.core.PdfCreator;
import io.github.holleymcfly.pdf.core.PdfCreatorBuilder;
import io.github.holleymcfly.pdf.model.PdfFormattedText;
import io.github.holleymcfly.pdf.model.color.PdfColorBuilder;
import io.github.holleymcfly.pdf.model.font.PdfFont;
import io.github.holleymcfly.pdf.model.font.PdfFontBuilder;
import io.github.holleymcfly.pdf.model.table.PdfTable;
import io.github.holleymcfly.pdf.model.table.PdfTableCell;
import io.github.holleymcfly.pdf.model.table.PdfTableCellBuilder;
import io.github.holleymcfly.pdf.model.table.PdfTableCellPosition;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class PdfCreatorTest {

    @Test
    // Don't run the test automatically as it creates a document in the local file system.
    // This is only for manual testing purposes.
    @Disabled
    public void createSimpleTextDocument() {

        PdfFont headerFont = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE))
                .withSize(8)
                .build();
        PdfFont headlineFont = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD))
                .withSize(22)
                .build();
        PdfFont contentFont = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN))
                .build();
        PdfFont contentFontBold = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD))
                .withSize(18)
                .build();
        PdfFont footerFont = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE))
                .withSize(8)
                .build();
        PdfFont tableHeaderFont = new PdfFontBuilder()
                .withFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD))
                .withSize(14)
                .withColor(PdfColorBuilder.createPdfColor(Color.BLUE))
                .build();

        String date = new SimpleDateFormat("EEEEE dd. MMMMM yyyy, HH:mm:ss").format(new Date());

        PdfCreator pdfCreator = new PdfCreatorBuilder()
                .withHeader("https://github.com/holleymcfly/pdf-creator", headerFont)
                .withFooter(date, footerFont)
                .withPageMarginLeft(60)
                .withPageMarginRight(40)
                .build();

        // Header
        pdfCreator.addTextCentered("Awesome pdf creator", headlineFont);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addNewLine(contentFont);

        // Content
        LinkedList<PdfFormattedText> line1 = new LinkedList<>();
        line1.add(new PdfFormattedText(LINE1_1, contentFont));
        line1.add(new PdfFormattedText(LINE1_2, contentFontBold));
        line1.add(new PdfFormattedText(LINE1_3, contentFont));
        pdfCreator.addTextCentered(line1);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned(LINE3, contentFont);

        // Some table
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned("Please see the following list:", tableHeaderFont);
        PdfTable table = createTable(contentFont);
        pdfCreator.addTable(table);

        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned(LINE2, contentFont);
        pdfCreator.addNewLine(contentFont);

        pdfCreator.save("C:/Temp/createdDocument.pdf");

        pdfCreator.closeDocument();
    }

    private PdfTable createTable(PdfFont contentFont) {

    	LinkedList<Float> columWidths = new LinkedList<>();
    	columWidths.add(100f);
    	columWidths.add(150f);
    	columWidths.add(70f);
    	columWidths.add(100f);
    	
        PdfTable table = new PdfTable(contentFont, columWidths);

        PdfTableCell cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(1, 1))
                .withContent("Dog Leash")
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(1, 2))
                .withContent("Organic Bike")
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(1, 4))
                .withContent("Tires for All")
                .withBackgroundColor(PdfColorBuilder.createPdfColor(Color.PINK))
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(2, 1))
                .withContent("Computer That Rock")
                .withFont(new PdfFontBuilder()
                        .withFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD))
                        .withColor(PdfColorBuilder.createPdfColor(47, 243, 12))
                        .build())
                .withBackgroundColor(PdfColorBuilder.createPdfColor(Color.GRAY))
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(2, 2, 2))
                .withContent("I Can't Believe It's Not Internet of Things!")
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(1, 3))
                .withContent("Yesterday Smack")
                .build();
        table.addCell(cell);
        cell = new PdfTableCellBuilder()
                .withPosition(new PdfTableCellPosition(2, 4))
                .withContent("Sneaky Rocker")
                .build();
        table.addCell(cell);
        return table;
    }

    private final static String LINE1_1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ornare dui nisl, in congue mauris tincidunt nec. Nam nec sapien eleifend, ";
    private final static String LINE1_2 = "aliquam purus vitae, iaculis ligula. ";
    private final static String LINE1_3 = "Donec non metus id nulla imperdiet molestie sed eget nibh. Mauris efficitur porttitor consectetur. Mauris imperdiet, mauris quis vehicula iaculis, massa metus lobortis felis, nec vehicula mauris enim vitae nulla. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam volutpat tristique tortor, ut pellentesque dui luctus non. Aliquam pharetra eleifend porttitor. Cras maximus ultricies eleifend. Nunc libero mi, facilisis quis fermentum sit amet, maximus nec libero. Praesent ultricies arcu sit amet velit aliquet, vel tincidunt odio iaculis. Aenean tempus odio ut est laoreet, sit amet bibendum justo efficitur. Nam ornare, enim a hendrerit lacinia, ligula libero tempus ipsum, sit amet luctus felis lectus sed dui. Nam dictum ultrices consequat.";
    private final static String LINE2 = "Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat.";
    private final static String LINE3 = "Curabitur at laoreet leo. Morbi ut ex metus. Donec egestas, orci et hendrerit dapibus, orci tortor malesuada lacus, eu rutrum eros mi ut quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer in ligula nec lacus mollis finibus ut vel elit. Phasellus auctor interdum tincidunt. In dapibus augue libero, at auctor augue dignissim ultricies. Vivamus finibus urna placerat odio volutpat porttitor. Vivamus ut posuere ligula, et interdum enim. Mauris luctus sodales justo. In a elit libero. Sed viverra et ex ut consectetur. Curabitur feugiat arcu ac nisi condimentum commodo. Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat. Curabitur at laoreet leo. Morbi ut ex metus. Donec egestas, orci et hendrerit dapibus, orci tortor malesuada lacus, eu rutrum eros mi ut quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer in ligula nec lacus mollis finibus ut vel elit. Phasellus auctor interdum tincidunt. In dapibus augue libero, at auctor augue dignissim ultricies. Vivamus finibus urna placerat odio volutpat porttitor. Vivamus ut posuere ligula, et interdum enim. Mauris luctus sodales justo. In a elit libero. Sed viverra et ex ut consectetur. Curabitur feugiat arcu ac nisi condimentum commodo. Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat.";
}
