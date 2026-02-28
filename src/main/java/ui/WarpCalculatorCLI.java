package main.java.ui;

import main.java.domain.Item;
import main.java.application.WarpCalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final double MIN_VALUE = 0.1; // minimaalne pikkus / fringe

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Lõimepikkuse kalkulaator");

            List<Item> items = new ArrayList<>();

            // Küsi esemete arv
            int itemCount = readPositiveInt(scanner, "Mitu eset soovid kududa? ");

            // Kas kõik esemed on ühesugused
            boolean allSame = readBoolean(scanner, "Kas kõik esemed on ühesugused? (jah/ei): ");

            double length = 0;
            boolean hasFringe = false;
            double fringeLength = 0;

            if (allSame) {
                // Küsi pikkus ainult üks kord
                length = readPositiveDouble(scanner, "Mis on esemete pikkus(cm)? ");
                hasFringe = readBoolean(scanner, "Kas esemel on narmad? (jah/ei): ");
                if (hasFringe) {
                    fringeLength = readPositiveDouble(scanner, "Mis on narmaste pikkus(cm)? ");
                }

                // Loo kõik itemid sama väärtusega
                for (int i = 0; i < itemCount; i++) {
                    items.add(new Item((int) length, hasFringe, (int) fringeLength));
                }
            } else {
                // Küsi iga itemi kohta eraldi
                for (int i = 0; i < itemCount; i++) {
                    System.out.println("=== " + (i + 1) + ". ese ===");
                    double itemLength = readPositiveDouble(scanner, "Mis on eseme pikkus(cm)? ");
                    boolean itemHasFringe = readBoolean(scanner, "Kas esemel on narmad? (jah/ei): ");
                    double itemFringeLength = 0;
                    if (itemHasFringe) {
                        itemFringeLength = readPositiveDouble(scanner, "Mis on narmaste pikkus(cm)? ");
                    }
                    items.add(new Item((int) itemLength, itemHasFringe, (int) itemFringeLength));
                }
            }

            // Kaduprotsent 0–100
            double lossPercentage = 0;
            while (true) {
                try {
                    System.out.print("Mis on lõimematerjali kaduprotsent (%): ");
                    lossPercentage = Double.parseDouble(scanner.nextLine().trim());
                    if (lossPercentage < 0 || lossPercentage > 100) {
                        System.out.println("Palun sisesta protsent 0–100.");
                        continue;
                    }
                    lossPercentage /= 100.0; // teisendame 0–1
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Palun sisesta korrektne number.");
                }
            }

            WarpCalculationService service = new WarpCalculationService();
            double warpLength = service.calculate(items, lossPercentage);

            System.out.printf("Kokku lõimepikkus: %.2f cm%n", warpLength);
        }
    }

    // --- Abifunktsioonid ---

    private static boolean readBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("jah")) return true;
            if (input.equals("ei")) return false;
            System.out.println("Palun sisesta ainult 'jah' või 'ei'.");
        }
    }

    private static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < MIN_VALUE) {
                    System.out.println("Palun sisesta positiivne number vähemalt " + MIN_VALUE + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Palun sisesta korrektne number.");
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value <= 0) {
                    System.out.println("Palun sisesta positiivne täisarv.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Palun sisesta korrektne täisarv.");
            }
        }
    }
}