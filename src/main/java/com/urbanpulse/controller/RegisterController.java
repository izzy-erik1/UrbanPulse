package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.exception.DuplicateEmailException;
import com.urbanpulse.model.User;
import com.urbanpulse.model.enums.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<UserRole> roleComboBox;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(UserRole.values());
        roleComboBox.getSelectionModel().select(UserRole.CITIZEN);
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        UserRole role = roleComboBox.getValue();

        try {
            User user = AppConfig.getInstance().getAuthService()
                    .register(name, email, password, role);
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Account created for " + user.getName() + "! You can now log in.");
        } catch (DuplicateEmailException | IllegalArgumentException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleGoToLogin() {
        messageLabel.setStyle("-fx-text-fill: orange;");
        messageLabel.setText("Screen navigation between Login/Register comes in a later polish step.");
    }
}