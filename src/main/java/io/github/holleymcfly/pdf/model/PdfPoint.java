package io.github.holleymcfly.pdf.model;

public class PdfPoint {

    private int x;
    private int y;

    public PdfPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
