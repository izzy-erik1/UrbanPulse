package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.exception.AuthenticationException;
import com.urbanpulse.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            User user = AppConfig.getInstance().getAuthService().login(email, password);
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Welcome, " + user.getName() + "! (Dashboard screen comes in a later step)");
        } catch (AuthenticationException e) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleGoToRegister() {
        errorLabel.setStyle("-fx-text-fill: orange;");
        errorLabel.setText("Register screen comes in the next step.");
    }
}