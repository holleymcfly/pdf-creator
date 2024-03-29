package io.github.holleymcfly.pdf.model;

public class PdfPoint {

    private final float x;
    private final float y;

    public PdfPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
