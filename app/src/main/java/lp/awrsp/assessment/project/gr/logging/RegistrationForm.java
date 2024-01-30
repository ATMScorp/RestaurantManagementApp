package lp.awrsp.assessment.project.gr.logging;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class RegistrationForm extends JFrame {
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JPasswordField confirmPasswordField;
    private final SignInForm signInForm;

    public RegistrationForm(SignInForm signInForm) {
        super("Registration");
        this.signInForm = signInForm;
        initializeForm();

        createRegistrationForm();
    }

    private void initializeForm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 220); // Increased window size
        setLocationRelativeTo(null);
    }

    private void createRegistrationForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add "Register" label at the top
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(new Font("Calibri", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerLabel, gbc);

        JLabel regUsernameLabel = new JLabel("Username:");
        regUsernameField = new JTextField();
        JLabel regPasswordLabel = new JLabel("Password:");
        regPasswordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(regUsernameLabel, gbc);
        gbc.gridy++;
        panel.add(regPasswordLabel, gbc);
        gbc.gridy++;
        panel.add(confirmPasswordLabel, gbc);

        panel.add(regUsernameField, gbc);
        gbc.gridy++;
        panel.add(regPasswordField, gbc);
        gbc.gridy++;
        panel.add(confirmPasswordField, gbc);

        // Set a higher weightx for the text fields to make them wider
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(regUsernameField, gbc);
        gbc.gridy++;
        panel.add(regPasswordField, gbc);
        gbc.gridy++;
        panel.add(confirmPasswordField, gbc);

        JButton signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(e -> performRegistration());

        Dimension buttonSize = new Dimension(80, 30);
        signUpButton.setPreferredSize(buttonSize);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(signUpButton, gbc);

        add(panel);

        setSize(450, 250);

        setVisible(true);
    }

    private void performRegistration() {
        String regUsername = regUsernameField.getText();
        char[] regPassword = regPasswordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        if (regUsername.isEmpty() || regPassword.length == 0 || confirmPassword.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Arrays.equals(regPassword, confirmPassword)) {
                signInForm.addCredentials(regUsername, new String(regPassword));

                dispose();
                signInForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
