package com.example.time_management_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

import java.io.IOException;

public class timeoff_screenController {

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label dateLabel;

    @FXML
    private DatePicker datePickerField;

    @FXML
    private Label timeoffLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label actualStatusLabel;

    /**
     * When the back button is pressed, closes the window
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * When the confirm button is pressed, checks if a date is selected
     * @param event
     */
    @FXML
    private void confirmButtonPressed(ActionEvent event) {
        LocalDate selectedDate = datePickerField.getValue();
        if (selectedDate != null) {
            actualStatusLabel.setText("Approved " + selectedDate.toString());
        } else {
            actualStatusLabel.setText("Error: No Date Selected. Please select a date.");
        }
    }

}
