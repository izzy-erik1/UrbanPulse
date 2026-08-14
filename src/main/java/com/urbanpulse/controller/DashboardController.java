package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.User;
import com.urbanpulse.model.enums.UserRole;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label notificationCountLabel;
    @FXML private ListView<String> issuesListView;
    @FXML private Button adminButton;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getName());

        boolean isStaffOrAdmin = user.getRole() == UserRole.ADMIN
                || user.getRole() == UserRole.MUNICIPALITY_STAFF;
        adminButton.setVisible(isStaffOrAdmin);
        adminButton.setManaged(isStaffOrAdmin);

        loadIssues();
        loadNotificationCount();
    }

    private void loadIssues() {
        List<Issue> issues = AppConfig.getInstance().getIssueService().getAll().stream()
                .filter(i -> i.getReporter().getId().equals(currentUser.getId()))
                .collect(Collectors.toList());

        List<String> display = issues.stream()
                .map(i -> "[" + i.getStatus() + "] " + i.getTitle())
                .collect(Collectors.toList());

        issuesListView.setItems(FXCollections.observableArrayList(display));
    }

    private void loadNotificationCount() {
        int unread = AppConfig.getInstance().getNotificationService()
                .getUnreadForUser(currentUser.getId()).size();
        notificationCountLabel.setText(unread + " unread notifications");
    }

    @FXML
    private void handleNewIssue() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/issue.fxml"));
            javafx.scene.Parent root = loader.load();

            IssueController issueController = loader.getController();
            issueController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfile() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/profile.fxml"));
            javafx.scene.Parent root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminPanel() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/admin.fxml"));
            javafx.scene.Parent root = loader.load();

            AdminController adminController = loader.getController();
            adminController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}