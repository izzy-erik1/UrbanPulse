package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label notificationCountLabel;
    @FXML private ListView<String> issuesListView;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getName());
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
        // Wired up in the Issue screen step
    }

    @FXML
    private void handleProfile() {
        // Wired up in the Profile screen step
    }
}