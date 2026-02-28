package main.java.domain;

import java.util.List;

public class WarpLengthCalculator {
    private static final int STARTING_WARP_CM = 50;
    private static final int ENDING_WARP_CM = 30;
    private static final double FRINGELESS_CM = 0.5;

    public double calculateWarpLength(List<Item> items, double lossPercentage) {
        if (items == null || items.isEmpty()) {
            return STARTING_WARP_CM + ENDING_WARP_CM;
        }

        double total = STARTING_WARP_CM + ENDING_WARP_CM;

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            double baseLength = item.getLengthCm();
            double loss = baseLength * lossPercentage;
            total += baseLength + loss;

            if (i == 0 || i == items.size() - 1) {
                // esimesel ja viimasel esemel arvestame vaid 1 korda narmaste pikkuse
                double fringeLength = item.hasFringe() ? item.getFringeLengthCm() : FRINGELESS_CM;
                total += fringeLength;
            } else {
                // teistel esemetel arvestame 2 korda narmaste pikkuse
                double fringeLength = item.hasFringe() ? item.getTotalFringeLengthCm() : FRINGELESS_CM * 2;
                total += fringeLength;
            }
        }

        return total;
    }

}