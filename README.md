# pdf-creator
## High-level API for creating PDF documents, using apache pdfbox.

The goal of this project is to provide an open source API that is easy to use for creating pdf documents.
It uses the pdfbox project from apache, which offers good functionality, but isn't very easy to use.

For a detailed manual, see this [wiki page](https://github.com/holleymcfly/pdf-creator/wiki).

## Getting started
### Include the library with maven:
```
<!-- https://mvnrepository.com/artifact/io.github.holleymcfly/pdf-creator -->
<dependency>
    <groupId>io.github.holleymcfly</groupId>
    <artifactId>pdf-creator</artifactId>
    <version>1.2.0</version>
</dependency>
```
Please check the latest version [here](https://mvnrepository.com/artifact/io.github.holleymcfly/pdf-creator).

### Create a new instance
```
PdfCreator pdfCreator = new PdfCreatorBuilder().build();
```

### Add some text
```
pdfCreator.addTextLeftAligned("Some text", new PdfFontBuilder().build());
```

### Save your document
```
pdfCreator.save("C:/createdDocument.pdf");
```

That's it!!!


## [Handling Fonts](https://github.com/holleymcfly/pdf-creator/wiki/Handling-fonts)
The pdf-creator needs the font that shall be used in the document. The font is of type `PdfFont` that contains the font type, its size and its color.
A PdfFont is created using the PdfFontBuilder like this:
```
PdfFont font = new PdfFontBuilder().build();
```

## [Creating a new pdf-creator](https://github.com/holleymcfly/pdf-creator/wiki/Creating-new-pdf-creators)
Creating a new, simple pdf-creator is very easy by using its builder class:
```
PdfCreator pdfCreator = new PdfCreatorBuilder().build();
```

When creating a pdf-creator, some more information can be directly passed to it. See the chapter in the wiki page for details.


## Adding content
When the pdf-creator has been created, it will start adding content at the top of the page, with a default margin. If the document has a header, the margin starts from that header line.

### [Adding text](https://github.com/holleymcfly/pdf-creator/wiki/Adding-text)
Simple text can be added to the document like this:
```
pdfCreator.addTextLeftAligned("Some text", font);
```

There are more options for adding text and new lines. Visit the chapter in the wiki page for details.

### [Adding tables](https://github.com/holleymcfly/pdf-creator/wiki/Creating-tables)
pdf-creator can handle tables very easily. To add a table, you have to create a `PdfTable` object and add it to the document:
```
pdfCreator.addTable(table);
```

## Example
This is an example of a pdf created with pdf-creator:

![example_table01.jpg](https://github.com/holleymcfly/pdf-creator/blob/master/documentation_images/example_pdf01.jpg)
