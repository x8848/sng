package com.sng.Table;

public class Button {
    private int X;
    private int Y;

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public static final int width = 40;
    public static final int height = 6;

    public Button(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}