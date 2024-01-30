package lp.awrsp.assessment.project.gr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class ReceiptGeneratorTest {

    @Test
    void testGenerateReceipt() {
        Map<String, Double> priceList = new HashMap<>();
        priceList.put("Burger", 5.0);
        priceList.put("Shake", 2.0);
        priceList.put("Cola", 1.5);

        Map<String, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put("Burger", 2);
        itemQuantities.put("Shake", 3);
        itemQuantities.put("Cola", 1);

        ReceiptGenerator generator = new ReceiptGenerator(priceList, itemQuantities);
        String receipt = generator.generateReceipt();

        assertNotNull(receipt);
        assertTrue(receipt.contains("Total:"));

    }

    @Test
    void testItemCategorization() {
        Map<String, Double> priceList = new HashMap<>();
        priceList.put("Tea", 2.0);
        priceList.put("Pancake", 4.0);

        Map<String, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put("Tea", 1);
        itemQuantities.put("Pancake", 2);

        ReceiptGenerator generator = new ReceiptGenerator(priceList, itemQuantities);
        String receipt = generator.generateReceipt();

        assertTrue(receipt.contains("Tea"));
        assertTrue(receipt.contains("Pancake"));
    }

    @Test
    void testReceiptFormatting() {
        Map<String, Double> priceList = new HashMap<>();
        priceList.put("Water", 1.0);

        Map<String, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put("Water", 3);

        ReceiptGenerator generator = new ReceiptGenerator(priceList, itemQuantities);
        String receipt = generator.generateReceipt();

        assertTrue(receipt.startsWith("============================================"));
        assertTrue(receipt.endsWith("Thank you. Have a good day!!!\n"));
    }

    @Test
    void testMissingItems() {
        Map<String, Double> priceList = new HashMap<>();
        priceList.put("Burger", 5.0);
        priceList.put("Shake", 3.0);
        priceList.put("Cola", 2.0);

        Map<String, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put("Burger", 2);

        ReceiptGenerator receiptGenerator = new ReceiptGenerator(priceList, itemQuantities);
        String generatedReceipt = receiptGenerator.generateReceipt();

        assertFalse(generatedReceipt.contains("Shake"));
        assertFalse(generatedReceipt.contains("Cola"));
    }
}
