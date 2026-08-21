package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.exception.InvalidIssueException;
import com.urbanpulse.model.*;
import com.urbanpulse.model.enums.Priority;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public class IssueController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<Priority> priorityComboBox;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private ComboBox<Municipality> municipalityComboBox;
    @FXML private Label messageLabel;
    @FXML private ListView<String> commentsListView;
    @FXML private TextField newCommentField;

    private User currentUser;
    private Issue currentIssue; // set when viewing/commenting on an existing issue


    @FXML
    public void initialize() {
        priorityComboBox.getItems().addAll(Priority.values());

        List<Category> categories = AppConfig.getInstance().getIssueService().getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));

        List<Municipality> municipalities = AppConfig.getInstance().getMunicipalityService().getAll();
        municipalityComboBox.setItems(FXCollections.observableArrayList(municipalities));
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setCurrentIssue(Issue issue) {
        this.currentIssue = issue;
        loadComments();
    }

    @FXML
    private void handleSubmitIssue() {
        try {
            Issue issue = AppConfig.getInstance().getIssueService().createIssue(
                    titleField.getText(),
                    descriptionField.getText(),
                    priorityComboBox.getValue(),
                    currentUser,
                    municipalityComboBox.getValue(),
                    categoryComboBox.getValue()
            );
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Issue submitted: " + issue.getTitle());
            setCurrentIssue(issue);
        } catch (InvalidIssueException | NullPointerException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Please fill in all fields before submitting.");
        }
    }

    private void loadComments() {
        if (currentIssue == null) return;
        List<String> comments = AppConfig.getInstance().getCommentService()
                .getByIssue(currentIssue.getId()).stream()
                .map(c -> c.getAuthor().getName() + ": " + c.getContent())
                .collect(Collectors.toList());
        commentsListView.setItems(FXCollections.observableArrayList(comments));
    }

    @FXML
    private void handlePostComment() {
        if (currentIssue == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Submit or select an issue first before commenting.");
            return;
        }
        AppConfig.getInstance().getCommentService()
                .addComment(newCommentField.getText(), currentIssue, currentUser);
        newCommentField.clear();
        loadComments();
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) titleField.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            messageLabel.setText("Could not return to dashboard.");
        }
    }




}