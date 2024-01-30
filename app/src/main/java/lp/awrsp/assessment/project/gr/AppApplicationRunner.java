package lp.awrsp.assessment.project.gr;

import lp.awrsp.assessment.project.gr.logging.SignInForm;
import lp.awrsp.assessment.project.gr.view.RestaurantManagementSystem;

import javax.swing.*;

public class AppApplicationRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Runnable showRestaurantSystem = () -> {
                // Utworzenie i wyświetlenie głównego okna aplikacji, RestaurantManagementSystem.
                RestaurantManagementSystem mainFrame = new RestaurantManagementSystem();
                mainFrame.setVisible(true);
            };

            // Utworzenie formularza logowania z przekazanym Runnable, który pokaże RestaurantManagementSystem.
            SignInForm signInForm = new SignInForm(showRestaurantSystem);
            signInForm.setVisible(true);
        });
    }
}
