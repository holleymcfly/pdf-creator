# pdf-creator
## High-level API for creating PDF documents, using apache pdfbox.

The goal of this project is to provide an API that is easy to use for creating pdf documents.

## Defining fonts
The pdf-creator needs the font that shall be used in the document. The font is of type `PdfFont` that contains the font type and its size.
A PdfFont can be created like this:
```
PdfFont font = new PdfFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
```

## Creating a new pdf-creator
### Creating a simple document
To create a pdf-creator, the PdfCreatorBuilder has to be used like this:
```
PdfCreator pdfCreator = new PdfCreatorBuilder().build();
```

### Creating a document with header
The pdf-creator can add a standard header, which consists of some left-aligned text, followed by a line.
If the header is set when the page is created, it will be added to each page of the document.
```
PdfCreator pdfCreator = new PdfCreatorBuilder().withHeader("The header text", headerFont).build();
```

### Creating a document with footer
The pdf-creator can also add a footer that consists of a line and some text, for example:
```
String date = new SimpleDateFormat("EEEEE dd. MMMMM yyyy, HH:mm:ss").format(new Date());
PdfCreator pdfCreator = new PdfCreatorBuilder().withFooter(date, footerFont).build();
```

## Adding text
### Adding left aligned text
Adding text to the document is as easy as:
```
pdfCreator.addTextLeftAligned("Some text", font);
```

If the text doesn't fit in one line, it is wrapped to several lines, including creating new pages automatically.

### Adding centered text
Text can also be added centered:
```
pdfCreator.addTextCentered("Awesome pdf-creator", font);
```

## Adding a new, empty line
To add an empty line, write:
```
pdfCreator.addNewLine(font);
```

As the height of the new line depends on the font size, the font has to be passed for adding a new line, too.

