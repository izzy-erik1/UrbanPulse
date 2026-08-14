package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private Label roleLabel;
    @FXML private Label messageLabel;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        roleLabel.setText("Role: " + user.getRole());
    }

    @FXML
    private void handleSave() {
        try {
            User updated = AppConfig.getInstance().getUserService()
                    .updateProfile(currentUser.getId(), nameField.getText(), emailField.getText());
            this.currentUser = updated;
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Profile updated.");
        } catch (Exception e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Could not update profile: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) nameField.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            messageLabel.setText("Could not return to dashboard.");
        }
    }
}