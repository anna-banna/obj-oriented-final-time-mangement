package com.example.time_management_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.Objects;

public class EmployeeProcessor {
    // Makes list of employees
    private List<Employee> employeeList = new ArrayList<>();
    private String fileName;

    /**
     * Processes the information from a text file into array
     */
    public EmployeeProcessor() {
        fileName = System.getProperty("user.dir") + "/employees.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // strip the comma and assign the appropriate positions to the data going into the array
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                double wage = Double.parseDouble(parts[2]);
                String position = parts[3];
                double totalHoursWorked = Double.parseDouble(parts[4]);
                double currentWeekHours = Double.parseDouble(parts[5]);
                int lastUpdatedWeek = Integer.parseInt(parts[6]);

                // creates the Employee object with the data and adds to the list
                Employee employee = new Employee(id, name, wage, position, totalHoursWorked, currentWeekHours, lastUpdatedWeek);
                employeeList.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Error reading the employees file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    // rewrites the updated information to the file
    public void writeEmployeesToFile() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(java.math.RoundingMode.HALF_UP);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Employee employee : employeeList) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%d%n",
                        employee.getId(),
                        employee.getName(),
                        df.format(employee.getWage()),
                        employee.getPosition(),
                        df.format(employee.getTotalHoursWorked()),
                        df.format(employee.getCurrentWeekHours()),
                        employee.getLastUpdatedWeek()
                );
                writer.print(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  returns the employee associated with given id
     * @param id
     * @return
     */
    public Employee getEmployeeById(String id){
        for (Employee employee : employeeList){
            if (employee.getId().equals(id)){
                return employee;
            }
        }
        return null;
    }
}
