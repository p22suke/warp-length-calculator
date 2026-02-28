package main.java.domain;

public class Item {
    private final int lengthCm;
    private final boolean hasFringe;
    private final int fringeLengthCm;

    // konstruktor
    public Item(int lengthCm, boolean hasFringe, int fringeLengthCm) {
        this.lengthCm = lengthCm;
        this.hasFringe = hasFringe;
        this.fringeLengthCm = fringeLengthCm;
    }

    // getters
    public int getLengthCm() {
        return lengthCm;
    }

    public boolean hasFringe() {
        return hasFringe;
    }

    public int getFringeLengthCm() {
        return fringeLengthCm;
    }

    public int getTotalFringeLengthCm() {
        return hasFringe ? fringeLengthCm * 2 : 0;
    }

}