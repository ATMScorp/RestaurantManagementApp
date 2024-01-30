package lp.awrsp.assessment.project.gr;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class ReceiptGenerator {
    private final Map<String, Double> priceList;
    private final Map<String, Integer> itemQuantities;
    private final NumberFormat currencyFormat;

    public ReceiptGenerator(Map<String, Double> priceList, Map<String, Integer> itemQuantities) {
        this.priceList = priceList;
        this.itemQuantities = itemQuantities;
        this.currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    }

    public String generateReceipt() {
        double subtotalBreakfast = 0.0;
        double subtotalDrinks = 0.0;

        StringBuilder receipt = new StringBuilder("============================================\n");
        receipt.append("           Restaurant Management Systems\n");
        receipt.append("============================================\n");

        receipt.append("--------------------------------------------\n");

        for (Map.Entry<String, Double> entry : priceList.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            int quantity = itemQuantities.getOrDefault(itemName, 0);

            if (quantity > 0) {
                double itemCost = itemPrice * quantity;
                categorizeItemsAndAppendToReceipt(receipt, itemName, quantity, itemCost);
                if (itemName.toLowerCase().contains("shake") || itemName.toLowerCase().contains("cola")) {
                    subtotalDrinks += itemCost;
                } else if (itemName.toLowerCase().contains("burger")) {
                    subtotalBreakfast += itemCost;
                }
            }
        }

        double tax = calculateTax(subtotalBreakfast + subtotalDrinks);
        double total = subtotalBreakfast + subtotalDrinks + tax;

        appendReceiptTotals(receipt, subtotalBreakfast, subtotalDrinks, tax, total);
        return receipt.toString();
    }

    private void categorizeItemsAndAppendToReceipt(StringBuilder receipt, String itemName, int quantity, double itemCost) {
        receipt.append(String.format("%-30s %2dx %8s\n", itemName, quantity, currencyFormat.format(itemCost)));
    }

    private double calculateTax(double cost) {
        return cost * 0.1;
    }

    private void appendReceiptTotals(StringBuilder receipt, double subtotalBreakfast, double subtotalDrinks, double tax, double total) {
        receipt.append("--------------------------------------------\n");
        receipt.append(String.format("%-30s %8s\n", "Subtotal - Breakfast:", currencyFormat.format(subtotalBreakfast)));
        receipt.append(String.format("%-30s %8s\n", "Subtotal - Drinks:", currencyFormat.format(subtotalDrinks)));
        receipt.append(String.format("%-30s %8s\n", "Tax:", currencyFormat.format(tax)));
        receipt.append("============================================\n");
        receipt.append(String.format("%-30s %8s\n", "Total:", currencyFormat.format(total)));
        receipt.append("============================================\n");
        receipt.append("                Thank you. Have a good day!!!\n");
    }
}
