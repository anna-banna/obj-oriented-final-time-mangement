/**
 * Group 15: Time Management Project
 * Employee Class
 */

package com.example.time_management_project;

public class Employee {
    private String id;
    private String name;
    private double wage;
    private String position;
    private double totalHoursWorked;
    private double currentWeekHours;
    private int lastUpdatedWeek;

    // Constructor
    public Employee(String id, String name, double wage, String position, double totalHoursWorked, double currentWeekHours, int lastUpdatedWeek) {
        this.id = id;
        this.name = name;
        this.wage = wage;
        this.position = position;
        this.totalHoursWorked = totalHoursWorked;
        this.currentWeekHours = currentWeekHours;
        this.lastUpdatedWeek = lastUpdatedWeek;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWage() {
        return wage;
    }

    public String getPosition() {
        return position;
    }

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public double getCurrentWeekHours() {
        return currentWeekHours;
    }

    public int getLastUpdatedWeek() {
        return lastUpdatedWeek;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public void setCurrentWeekHours(double currentWeekHours) {
        this.currentWeekHours = currentWeekHours;
    }

    public void setLastUpdatedWeek(int lastUpdatedWeek) {
        this.lastUpdatedWeek = lastUpdatedWeek;
    }

    /**
     * Calculates the number of hours worked
     * @param hours
     */
    public void addHours(double hours) {
        this.totalHoursWorked = Math.round(this.totalHoursWorked * 60 + hours * 60) / 60.0;
        this.currentWeekHours = Math.round(this.currentWeekHours * 60 + hours * 60) / 60.0;
    }

    /**
     * Resets the number of hours worked in the current week to 0
     */
    public void resetCurrentWeekHours() {
        this.currentWeekHours = 0;
    }
}
