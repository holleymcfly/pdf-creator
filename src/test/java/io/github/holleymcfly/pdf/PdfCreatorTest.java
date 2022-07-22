package io.github.holleymcfly.pdf;

import io.github.holleymcfly.pdf.core.PdfCreator;
import io.github.holleymcfly.pdf.core.PdfCreatorBuilder;
import io.github.holleymcfly.pdf.model.PdfFont;
import io.github.holleymcfly.pdf.model.PdfTable;
import io.github.holleymcfly.pdf.model.PdfTableCell;
import io.github.holleymcfly.pdf.model.PdfTableCellPosition;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfCreatorTest {

    @Test
    public void createSimpleTextDocument() {

        PdfFont headerFont = new PdfFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 8);
        PdfFont headlineFont = new PdfFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 22);
        PdfFont contentFont = new PdfFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
        PdfFont footerFont = new PdfFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 8);

        String date = new SimpleDateFormat("EEEEE dd. MMMMM yyyy, HH:mm:ss").format(new Date());

        PdfCreator pdfCreator = new PdfCreatorBuilder()
                .withHeader("Some documentation", headerFont)
                .withFooter(date, footerFont)
                .build();

        // Header
        pdfCreator.addTextCentered("Awesome targenio pdf creator", headlineFont);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addNewLine(contentFont);

        // Content
        pdfCreator.addTextLeftAligned(LINE1, contentFont);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextCentered(LINE3, contentFont);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned(LINE4, contentFont);
        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned(LINE5, contentFont);

        // Some table
        PdfTable table = createTable(contentFont);
        pdfCreator.addTable(table);

        pdfCreator.addNewLine(contentFont);
        pdfCreator.addTextLeftAligned(LINE2, contentFont);
        pdfCreator.addNewLine(contentFont);

        pdfCreator.save("C:/Temp/createdDocument.pdf");

        pdfCreator.closeDocument();
    }

    private PdfTable createTable(PdfFont contentFont) {

        PdfTable table = new PdfTable(contentFont, 400, 4);
        PdfTableCell cell = new PdfTableCell(new PdfTableCellPosition(1, 1), "Cell 1/1");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(1, 2), "WWWWWWWW");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(2, 3), "Cell 2/3");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(1, 4), "Cell 1/4");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(2, 1), "Cell 2/1");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(2, 2), "Cell 2/2 with a little bit more text that doesn't fit in the cell.");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(1, 3), "Cell 1/3");
        table.addCell(cell);
        cell = new PdfTableCell(new PdfTableCellPosition(2, 4), "Cell 2/4");
        table.addCell(cell);
        return table;
    }

    private final static String LINE1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ornare dui nisl, in congue mauris tincidunt nec. Nam nec sapien eleifend, aliquam purus vitae, iaculis ligula. Donec non metus id nulla imperdiet molestie sed eget nibh. Mauris efficitur porttitor consectetur. Mauris imperdiet, mauris quis vehicula iaculis, massa metus lobortis felis, nec vehicula mauris enim vitae nulla. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam volutpat tristique tortor, ut pellentesque dui luctus non. Aliquam pharetra eleifend porttitor. Cras maximus ultricies eleifend. Nunc libero mi, facilisis quis fermentum sit amet, maximus nec libero. Praesent ultricies arcu sit amet velit aliquet, vel tincidunt odio iaculis. Aenean tempus odio ut est laoreet, sit amet bibendum justo efficitur. Nam ornare, enim a hendrerit lacinia, ligula libero tempus ipsum, sit amet luctus felis lectus sed dui. Nam dictum ultrices consequat.";
    private final static String LINE2 = "Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat.";
    private final static String LINE3 = "Curabitur at laoreet leo. Morbi ut ex metus. Donec egestas, orci et hendrerit dapibus, orci tortor malesuada lacus, eu rutrum eros mi ut quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer in ligula nec lacus mollis finibus ut vel elit. Phasellus auctor interdum tincidunt. In dapibus augue libero, at auctor augue dignissim ultricies. Vivamus finibus urna placerat odio volutpat porttitor. Vivamus ut posuere ligula, et interdum enim. Mauris luctus sodales justo. In a elit libero. Sed viverra et ex ut consectetur. Curabitur feugiat arcu ac nisi condimentum commodo. Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat.";
    private final static String LINE4 = "Nullam ac euismod justo, ac accumsan erat. Sed aliquam nisi id porta vulputate. Quisque auctor mi eu porttitor vulputate. Duis magna purus, lacinia quis sollicitudin vel, convallis a dolor. Curabitur consectetur in odio vitae ultrices. Donec ante sem, dapibus vitae tortor a, condimentum gravida eros. Maecenas eget nulla urna. Mauris lorem urna, sagittis eget vehicula quis, consectetur fermentum tortor. In accumsan bibendum consequat. Donec accumsan nulla tincidunt sapien rhoncus, et ullamcorper nunc faucibus. Pellentesque quam velit, fermentum id lacus a, aliquam eleifend enim. Aenean non mattis arcu. Maecenas condimentum elit ac consequat semper. Maecenas sollicitudin suscipit dolor in mollis. Proin quis porttitor urna. Mauris bibendum finibus ex et lacinia. Curabitur viverra ornare est, a interdum augue pulvinar ultricies. Mauris eu libero eleifend, efficitur felis et, volutpat odio. Duis ex eros, pretium et diam a, posuere varius magna. Vestibulum porta erat et est fringilla suscipit. Suspendisse luctus turpis vel tincidunt ullamcorper. Duis quis purus tellus. Nunc sit amet lectus sed ante consequat lobortis et a elit. In maximus ultricies mauris vel imperdiet. Nulla pulvinar semper nisl at tristique. Morbi nec turpis eu odio pretium blandit. Fusce id est ullamcorper, ultrices est in, interdum elit. Nullam at lobortis erat.";
    private final static String LINE5 = "Nullam accumsan venenatis massa, id volutpat urna feugiat non. Nam tincidunt viverra tristique. Curabitur rhoncus faucibus dui quis consequat. Donec rutrum, justo consectetur aliquam consectetur, nulla sem feugiat mauris, in ultricies sapien eros rhoncus eros. Nullam fermentum suscipit ex. Proin volutpat urna eu ornare iaculis. Vestibulum sed ullamcorper nisl. Donec lacinia, lacus at porttitor vehicula, mauris mi varius purus, nec commodo felis eros a nunc. Maecenas et sapien urna. Aliquam a libero dignissim, pretium metus at, posuere velit. Vestibulum mi urna, suscipit suscipit sem sodales, ullamcorper hendrerit metus. Aenean faucibus a eros eget tincidunt. Curabitur at laoreet leo. Morbi ut ex metus. Donec egestas, orci et hendrerit dapibus, orci tortor malesuada lacus, eu rutrum eros mi ut quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer in ligula nec lacus mollis finibus ut vel elit. Phasellus auctor interdum tincidunt. In dapibus augue libero, at auctor augue dignissim ultricies. Vivamus finibus urna placerat odio volutpat porttitor. Vivamus ut posuere ligula, et interdum enim. Mauris luctus sodales justo. In a elit libero. Sed viverra et ex ut consectetur. Curabitur feugiat arcu ac nisi condimentum commodo.";
}
