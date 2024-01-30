package lp.awrsp.assessment.project.gr.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RestaurantManagementSystem extends JFrame implements ActionListener {


    private final Map<String, JSpinner> itemSpinners;
    private final JButton btnTotal;
    private final JButton btnReceipt;
    private final JButton btnReset;
    private final JButton btnExit;
    private final JTextArea textReceipt;
    private final Map<String, Double> priceList = new HashMap<>();

    public RestaurantManagementSystem() {

        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setMaximizedBounds(env.getMaximumWindowBounds());
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);


        priceList.put("Filet-O-Fish", 3.99);
        priceList.put("Chicken Burger", 4.99);
        priceList.put("Chicken Burger Meal", 15.99);
        priceList.put("Bacon & Chesse Burger", 6.99);
        priceList.put("Bacon & Chesse Burger Meal", 18.99);
        priceList.put("Strips", 2.99);
        priceList.put("Milk Shake", 1.99);
        priceList.put("Grape juice", 1.99);
        priceList.put("Vanilla Shake", 1.99);
        priceList.put("Cola", 1.99);


        JPanel panelItems = new JPanel(new GridLayout(0, 2, 20, 20));
        panelItems.setBorder(BorderFactory.createTitledBorder("Items"));

        itemSpinners = new HashMap<>();

        for (String itemName : priceList.keySet()) {
            JLabel label = new JLabel(itemName);
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

            itemSpinners.put(itemName, spinner);

            panelItems.add(label);
            panelItems.add(spinner);
        }


        JPanel panelButtons = new JPanel();
        btnTotal = new JButton("Total");
        btnReceipt = new JButton("Receipt");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        btnTotal.addActionListener(this);
        btnReceipt.addActionListener(this);
        btnReset.addActionListener(this);
        btnExit.addActionListener(this);

        panelButtons.add(btnTotal);
        panelButtons.add(btnReceipt);
        panelButtons.add(btnReset);
        panelButtons.add(btnExit);


        textReceipt = new JTextArea();
        textReceipt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textReceipt);
        scrollPane.setPreferredSize(new Dimension(300, this.getHeight()));


        add(scrollPane, BorderLayout.WEST); // Receipt on the left
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.add(panelItems);
        add(orderPanel, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);


        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTotal) {
            calculateTotal();
        } else if (e.getSource() == btnReceipt) {
            generateReceipt();
        } else if (e.getSource() == btnReset) {
            resetForm();
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    private void calculateTotal() {
        double total = calculateCost();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedTotal = currencyFormat.format(total);
        JOptionPane.showMessageDialog(this, "Total Cost: " + formattedTotal);
    }

    private void generateReceipt() {
        double subtotalBreakfast = 0.0;
        double subtotalDrinks = 0.0;
        double total;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        StringBuilder receipt = new StringBuilder("============================================\n");
        receipt.append("           Restaurant Management Systems\n");
        receipt.append("============================================\n");
        receipt.append("Reference: ").append(generateReferenceNumber()).append("\n");
        receipt.append("Date: ").append(getCurrentDate()).append("  Time: ").append(getCurrentTime()).append("\n");
        receipt.append("--------------------------------------------\n");

        for (Map.Entry<String, Double> entry : priceList.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            int quantity = (int) itemSpinners.get(itemName).getValue();

            if (quantity > 0) {
                double itemCost = itemPrice * quantity;

                // Adjust the logic to categorize items based on your specific criteria
                if (itemName.toLowerCase().contains("shake") || itemName.toLowerCase().contains("cola")) {
                    subtotalDrinks += itemCost;
                    receipt.append(String.format("%-30s %2dx %8s\n", itemName, quantity, currencyFormat.format(itemCost)));
                } else if (itemName.toLowerCase().contains("burger")) {
                    subtotalBreakfast += itemCost;
                    receipt.append(String.format("%-30s %2dx %8s\n", itemName, quantity, currencyFormat.format(itemCost)));
                } else {
                    receipt.append(String.format("%-30s %2dx %8s\n", itemName, quantity, currencyFormat.format(itemCost)));
                }
            }
        }

        double tax = calculateTax(subtotalBreakfast + subtotalDrinks);

        receipt.append("--------------------------------------------\n");
        receipt.append(String.format("%-30s %8s\n", "Subtotal - Breakfast:", currencyFormat.format(subtotalBreakfast)));
        receipt.append(String.format("%-30s %8s\n", "Subtotal - Drinks:", currencyFormat.format(subtotalDrinks)));
        receipt.append(String.format("%-30s %8s\n", "Tax:", currencyFormat.format(tax)));
        receipt.append("============================================\n");
        total = subtotalBreakfast + subtotalDrinks + tax;
        receipt.append(String.format("%-30s %8s\n", "Total:", currencyFormat.format(total)));
        receipt.append("============================================\n");
        receipt.append("                Thank you. Have a good day!!!\n");

        textReceipt.setText(receipt.toString());
    }



    private double calculateCost() {
        double cost = 0.0;

        for (Map.Entry<String, Double> entry : priceList.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            int quantity = (int) itemSpinners.get(itemName).getValue();

            cost += itemPrice * quantity;
        }

        return cost;
    }

    private double calculateTax(double cost) {

        return cost * 0.1;
    }

    private String generateReferenceNumber() {
        // Generate a unique reference number based on the current timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "RMS" + timestamp;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(new Date());
    }

    private void resetForm() {
        for (JSpinner spinner : itemSpinners.values()) {
            spinner.setValue(0);
        }
        textReceipt.setText("");
    }
}
