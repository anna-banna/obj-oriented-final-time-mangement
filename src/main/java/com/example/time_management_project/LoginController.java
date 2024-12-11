package com.example.time_management_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    public EmployeeProcessor employeeAppProcessor = new EmployeeProcessor();
    public List<Employee> employeeList = employeeAppProcessor.getEmployeeList();

    @FXML
    private TextField employeeIdField;

    @FXML
    private Button loginButton;

    /**
     * Checks if the employee ID is in the list of employees
     * @return
     */
    private boolean authenticateEmployee() {
        String givenId = employeeIdField.getText();
        for (Employee employee : employeeList) {
            if (employee.getId().equals(givenId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * When the login button is pressed, checks if the employee ID is in the list of employees
     * If it is, it will open the main screen
     * If it is not, it will display an error message
     * @param event
     */
    @FXML
    public void loginButtonPressed(ActionEvent event) {
        if (!authenticateEmployee()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect ID! Please try again.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_screen.fxml"));
            Parent root = loader.load();

            mainscreenController mainScreen = loader.getController();
            mainScreen.setEmployeeId(employeeIdField.getText());

            // hide the login screen
            ((Node) (event.getSource())).getScene().getWindow().hide();

            // create a new scene and stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // set and show the new stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}