package com.example.time_management_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import java.io.IOException;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class mainscreenController {
    private EmployeeProcessor employeeProcessor = new EmployeeProcessor();
    private String employeeId;
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    @FXML
    private Label positionLabel;
    @FXML
    private Label wageLabel;
    @FXML
    private Label currentPayLabel;
    @FXML
    private Label totalPayLabel;

    @FXML
    private Button buttonClockin;
    @FXML
    private Label clockInTimeLabel;
    @FXML
    private Label clockOutTimeLabel;

    @FXML
    private Button buttonClockout;

    @FXML
    private Button buttonRTO;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label nameLabel;

    /**
     * Sets the current time the clock in button is pressed
     * @param event
     */
    @FXML
    void clockinPressed(ActionEvent event) {
        clockInTime = LocalDateTime.now();
        clockInTimeLabel.setText("Clock In Time: " + clockInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * Sets the current time the clock out button is pressed
     * @param event
     */
    @FXML
    void clockoutPressed(ActionEvent event) {
        clockOutTime = LocalDateTime.now();
        clockOutTimeLabel.setText("Clock Out Time: " + clockOutTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // Calls updateEmployeeHours() method once clock out is pressed
        updateEmployeeHours();
        // Calls writeEmployeesToFile() method to write the information to the file
        employeeProcessor.writeEmployeesToFile();
    }

    @FXML
    void timeoffPressed(ActionEvent event) {
        try {
            // Load the timeoff_screen.fxml
            Parent root = FXMLLoader.load(getClass().getResource("timeoff_screen.fxml"));

            // Create a new scene and stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Set and show the new stage
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls all the relevant information needed on the GUI based on the entered employee id
     * @param id
     */
    public void setEmployeeId(String id){
        employeeId = id;
        welcomeLabel.setText("Employee ID: " + id);
        setPositionFromId(id);
        setWageFromId(id);
        setCurrentPayFromId(id);
        setTotalPayFromId(id);
        setNameFromId(id);
    }

    /**
     * Finds position based on entered id
     * @param id
     */
    private void setPositionFromId(String id) {
        Employee employee = employeeProcessor.getEmployeeById(id);
        if (employee != null) {
            String position = employee.getPosition();
            positionLabel.setText("Position: " + position);
        } else {
            positionLabel.setText("Position: Not Found");
        }
    }

    /**
     * Finds name based on entered id
     * @param id
     */
    private void setNameFromId(String id){
        Employee employee = employeeProcessor.getEmployeeById(id);
        if (employee != null){
            String name = employee.getName();
            nameLabel.setText("Name: " + name);
        } else {
            nameLabel.setText("Name: Not Found");
        }
    }

    /**
     * Finds wage based on entered id
     * @param id
     */
    private void setWageFromId(String id) {
        Employee employee = employeeProcessor.getEmployeeById(id);
        if (employee != null) {
            String wageFormatted = String.format("%.2f", employee.getWage());
            wageLabel.setText("Wage: $" + wageFormatted);
        } else {
            wageLabel.setText("Wage: Not Found");
        }
    }

    /**
     * finds wage based on entered id
     * @param id
     */
    private void setCurrentPayFromId(String id){
        Employee employee = employeeProcessor.getEmployeeById(id);
        if (employee != null){
            double currentPay = employee.getWage() * employee.getCurrentWeekHours();
            String currentPayFormatted = String.format("%.2f", currentPay);
            currentPayLabel.setText("Current Week's Pay: $" + currentPayFormatted);
        } else {
            currentPayLabel.setText("Current Week's Pay: Not Found");
        }
    }

    /**
     * finds total pay based on entered id
     * @param id
     */
    private void setTotalPayFromId(String id){
        Employee employee = employeeProcessor.getEmployeeById(id);
        if (employee != null){
            double totalPay = employee.getTotalHoursWorked() * employee.getWage();
            String totalPayFormatted = String.format("%.2f", totalPay);
            totalPayLabel.setText("Total Pay: $" + totalPayFormatted);
        } else {
            totalPayLabel.setText("Total Pay: Not Found");
        }
    }


    /**
     * Updates the hours the employee works based on clockInTime and clockOutTime
     */
    private void updateEmployeeHours() {
        if (clockInTime != null && clockOutTime != null) {
            Duration duration = Duration.between(clockInTime, clockOutTime);

            // Convert the duration to total minutes and round to nearest minute
            long totalMinutes = Math.round((double) duration.toSeconds() / 60);

            // Convert minutes to hours (decimal)
            double hoursWorked = totalMinutes / 60.0;

            for (Employee employee : employeeProcessor.getEmployeeList()) {
                if (employee.getId().equals(employeeId)) {
                    // Calls checkAndResetWeek() method to check the last week the employee clocked in
                    checkAndResetWeek(employee);
                    employee.addHours(hoursWorked);
                    break;
                }
            }
        }
    }

    /**
     * Checks if the week has changed since the last time the employee clocked in
     * @param employee
     */
    private void checkAndResetWeek(Employee employee) {
        int currentWeek = getCurrentWeekOfYear();
        if (employee.getLastUpdatedWeek() != currentWeek) {
            employee.resetCurrentWeekHours();
        }
        // Update with the current week which goes into the file
        employee.setLastUpdatedWeek(currentWeek);
    }

    /**
     * Gets the current week of the year
     * @return
     */
    private static int getCurrentWeekOfYear() {
        LocalDateTime now = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return now.get(weekFields.weekOfWeekBasedYear());
    }
}


