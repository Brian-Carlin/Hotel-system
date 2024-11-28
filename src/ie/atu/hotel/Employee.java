//Brian Carlin 
//L00173257@atu.ie
//Employee class
//26/11/2024

package ie.atu.hotel;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.swing.*;

public class Employee extends Person implements Payable, Serializable {
    private double salary;
    private int number;

    // Fields for title, first name, and surname directly in Employee
    private String title;
    private String firstName;
    private String surname;

    private static int nextNumber = 10000; // Static field for unique employee IDs
    private final double MAX_SALARY = 150000;

    // Default Constructor
    public Employee() {
        super();
        this.salary = 0.0;
        this.number = nextNumber++; // Assign current value, then increment
        this.title = "";
        this.firstName = "";
        this.surname = "";
    }

    // Initialization Constructor
    public Employee(String title, String firstName, String surname, String phoneNo, double salary) {
        super(name, phoneNo); // Initialize fields inherited from Person (assuming 'Person' has a constructor with phoneNo)
        this.salary = salary;
        this.number = nextNumber++;
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
    }

    // Overridden toString method
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");

        // Create full name string
        String fullName = this.title + " " + this.firstName + " " + this.surname;

        // Return all details in a single line
        return "Employee Number: " + number + " | " +
               "Name: " + fullName + " | " +
               "Phone Number: " + this.phoneNumber + " | " +
               "Salary: €" + df.format(salary);
    }

    // Overridden equals method to compare employees based on their employee number
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee otherEmployee = (Employee) obj;
            return this.number == otherEmployee.number;
        }
        return false;
    }

    // Getters and setters for title, first name, surname, and salary
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getNumber() {
        return number;
    }
    
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    // Setter for next number (used when deserializing employees)
    public static void setNextNumber(int newNextNumber) {
        nextNumber = newNextNumber;
    }

    // Method to increment salary
    @Override
    public double incrementSalary(double incrementAmount) {
        salary += incrementAmount;
        if (salary > MAX_SALARY) {
            salary = MAX_SALARY;
        }
        return salary;
    }

    // read() method for user input using JOptionPane
    @Override
    public boolean read() {
        // Create input fields for the dialog box
        JComboBox<String> cbTitle = new JComboBox<>(new String[]{"Mr", "Ms", "Mrs", "Miss"});
        JTextField txtFirstName = new JTextField();
        JTextField txtSurname = new JTextField();
        JTextField txtPhoneNumber = new JTextField();
        JTextField txtSalary = new JTextField();

        // Add these fields to a dialog box
        Object[] message = {
            "Title:", cbTitle,
            "First Name:", txtFirstName,
            "Surname:", txtSurname,
            "Phone Number:", txtPhoneNumber,
            "Salary:", txtSalary
        };

        // Create the dialog box
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true); // Ensure the dialog stays on top

        // Display the dialog box and capture the user's response
        int option = JOptionPane.showConfirmDialog(dialog, message, 
                                                   "Enter Employee Details", 
                                                   JOptionPane.OK_CANCEL_OPTION);

        // If OK is selected, validate and set the fields
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Set the employee fields
                this.title = (String) cbTitle.getSelectedItem();
                this.firstName = txtFirstName.getText();
                this.surname = txtSurname.getText();
                this.phoneNumber = txtPhoneNumber.getText();
                this.salary = Double.parseDouble(txtSalary.getText()); // Parse salary

                return true; // Indicate success
            } catch (NumberFormatException e) {
                // If salary is invalid, show an error message
                JOptionPane.showMessageDialog(null, "Invalid salary. Please enter a valid number.", 
                                              "Input Error", JOptionPane.ERROR_MESSAGE);
                return false; // Indicate failure
            }
        }

        // If Cancel is selected, return false
        return false;
    }


}
