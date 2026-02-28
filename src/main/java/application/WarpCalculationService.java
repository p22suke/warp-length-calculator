package main.java.application;

import main.java.domain.WarpLengthCalculator;
import main.java.domain.Item;

import java.util.List;

public class WarpCalculationService {
 private final WarpLengthCalculator calculator;
 // konstruktor
 public WarpCalculationService(){
    this.calculator = new WarpLengthCalculator();
 }

 public double calculate(List<Item> items, double lossPercentage) {
    return calculator.calculateWarpLength(items, lossPercentage);
 }

}
