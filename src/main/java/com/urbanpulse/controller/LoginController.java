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

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(user);

            javafx.stage.Stage stage = (javafx.stage.Stage) emailField.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));

        } catch (AuthenticationException e) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Something went wrong loading the dashboard.");
        }
    }
    @FXML
    private void handleGoToRegister() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/register.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) emailField.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            errorLabel.setText("Could not open register screen.");
        }
    }
}