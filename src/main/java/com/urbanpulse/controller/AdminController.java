package com.urbanpulse.controller;

import com.urbanpulse.config.AppConfig;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.Municipality;
import com.urbanpulse.model.User;
import com.urbanpulse.model.enums.IssueStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    @FXML private ComboBox<Municipality> municipalityFilterComboBox;
    @FXML private ComboBox<IssueStatus> statusFilterComboBox;
    @FXML private ComboBox<IssueStatus> newStatusComboBox;
    @FXML private ListView<String> issuesListView;
    @FXML private Label messageLabel;

    private User currentUser;
    private List<Issue> currentIssues;

    @FXML
    public void initialize() {
        List<Municipality> municipalities = AppConfig.getInstance().getMunicipalityService().getAll();
        municipalityFilterComboBox.setItems(FXCollections.observableArrayList(municipalities));

        statusFilterComboBox.getItems().addAll(IssueStatus.values());
        newStatusComboBox.getItems().addAll(IssueStatus.values());
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        handleShowAll();
    }

    @FXML
    private void handleApplyFilters() {
        Municipality municipality = municipalityFilterComboBox.getValue();
        IssueStatus status = statusFilterComboBox.getValue();

        if (municipality != null) {
            currentIssues = AppConfig.getInstance().getIssueService()
                    .getByMunicipality(municipality.getId());
        } else if (status != null) {
            currentIssues = AppConfig.getInstance().getIssueService().getByStatus(status);
        } else {
            currentIssues = AppConfig.getInstance().getIssueService().getAll();
        }
        refreshList();
    }

    @FXML
    private void handleShowAll() {
        currentIssues = AppConfig.getInstance().getIssueService().getAll();
        refreshList();
    }

    private void refreshList() {
        List<String> display = currentIssues.stream()
                .map(i -> "#" + i.getId() + " [" + i.getStatus() + "] " + i.getTitle()
                        + " (" + i.getMunicipality().getName() + ")")
                .collect(Collectors.toList());
        issuesListView.setItems(FXCollections.observableArrayList(display));
    }

    @FXML
    private void handleUpdateStatus() {
        int selectedIndex = issuesListView.getSelectionModel().getSelectedIndex();
        IssueStatus newStatus = newStatusComboBox.getValue();

        if (selectedIndex < 0 || newStatus == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Select an issue and a new status first.");
            return;
        }

        Issue selected = currentIssues.get(selectedIndex);
        AppConfig.getInstance().getIssueService().changeStatus(selected.getId(), newStatus);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Status updated for issue #" + selected.getId());
        handleShowAll();
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(currentUser);

            javafx.stage.Stage stage = (javafx.stage.Stage) issuesListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 900, 600));
        } catch (Exception e) {
            messageLabel.setText("Could not return to dashboard.");
        }
    }
}