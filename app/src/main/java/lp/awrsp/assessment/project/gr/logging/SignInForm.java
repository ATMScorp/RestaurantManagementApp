package lp.awrsp.assessment.project.gr.logging;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignInForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Map<String, String> credentialsMap;
    private final Runnable onSuccessfulSignIn;

    public SignInForm(Runnable onSuccessfulSignIn) {
        super("Login");
        this.onSuccessfulSignIn = onSuccessfulSignIn;
        initializeForm();
        createLoginForm();
    }

    private void initializeForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 150);
        setLocationRelativeTo(null);
        credentialsMap = new HashMap<>();
        // Example credentials for testing
        credentialsMap.put("user", "pass");
    }
    private void createLoginForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel imagePanel = new JPanel();
        ImageIcon icon = new ImageIcon("C:\\Users\\Alex\\Documents\\GitHub\\lp-awrsp-2023-2024-assessment-project-gr-2\\app\\src\\main\\java\\lp\\awrsp\\assessment\\project\\gr\\cookig.png");
        JLabel imageLabel = new JLabel(icon);
        imagePanel.add(imageLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        panel.add(imagePanel, gbc);
        createWelcomeMessage();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> performLogin());

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> showRegistrationForm());

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(loginButton);
        inputPanel.add(registerButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        panel.add(inputPanel, gbc);

        add(panel);
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (credentialsMap.containsKey(username) && Arrays.equals(credentialsMap.get(username).toCharArray(), password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (onSuccessfulSignIn != null) {
                    onSuccessfulSignIn.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void addCredentials(String username, String password) {
        credentialsMap.put(username, password);
    }

    private void createWelcomeMessage() {
        JLabel welcomeLabel = new JLabel("Welcome to the restaurant management system!");
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);

        // Add the welcome message panel at the top
        add(welcomePanel, BorderLayout.NORTH);
    }

    private void showRegistrationForm() {
        dispose();
        RegistrationForm registrationForm = new RegistrationForm(this);
        registrationForm.setVisible(true);
    }
}
