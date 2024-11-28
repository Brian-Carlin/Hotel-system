//Brian Carlin 
//L00173257@atu.ie
//EmployeeSerializer class
//26/11/2024

package ie.atu.serialize;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import ie.atu.hotel.Employee;

public class EmployeeSerializer {
    private ArrayList<Employee> employees;
    private final String FILENAME = "employees.bin";
    private File employeesFile;

    // Default Constructor
    public EmployeeSerializer() {
        employeesFile = new File(FILENAME);  // "employees.bin"
        try {
            if (!employeesFile.exists()) {
                employeesFile.createNewFile(); // Create the file if it does not exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////
    // Method Name : add()                                     //
    // Return Type : void                                      //
    // Parameters : None                                       //
    // Purpose : Reads one Employee record from the user       //
    //           and adds it to the ArrayList called employees //
    /////////////////////////////////////////////////////////////
    public void add() {
        // Create a new Employee object
        Employee employee = new Employee();

        // Use the enhanced read() method to gather details
        if (employee.read()) {
            // If read() is successful, add the employee to the list
            employees.add(employee);
            JOptionPane.showMessageDialog(null, "Employee added successfully!", 
                                          "Add Employee", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If read() returns false, display a cancellation message
            JOptionPane.showMessageDialog(null, "Employee addition canceled.", 
                                          "Add Employee", JOptionPane.WARNING_MESSAGE);
        }
    }

    ///////////////////////////////////////////////////////
    // Method Name : list()                              //
    // Return Type : void                                //
    // Parameters : None                                 //
    // Purpose : Lists all Employee records in employees //
    ///////////////////////////////////////////////////////
    public void list() {
        StringBuilder employeesToList = new StringBuilder();

        if (!employees.isEmpty()) {
            for (Employee tmpEmployee : employees) {
                employeesToList.append(tmpEmployee).append("\n");
            }
            JOptionPane.showMessageDialog(null, employeesToList.toString(), "EMPLOYEE LIST", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No Employees to list.", "EMPLOYEE LIST", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //////////////////////////////////////////////////////////////////
    // Method Name : view()                                         //
    // Return Type : Employee                                       //
    // Parameters : None                                            //
    // Purpose : Displays the required Employee record on screen    //
    //         : And returns it, or null if not found               //
    //////////////////////////////////////////////////////////////////
    public Employee view() {
        // Prompt the user for the employee number
        String input = JOptionPane.showInputDialog(null, "Enter Employee Number:");

        // If the user cancels or enters an invalid input
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input entered or canceled", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            // Parse the input to an integer (Employee number)
            int employeeNumber = Integer.parseInt(input);

            // Search for the employee in the employees list
            for (Employee employee : employees) {
                if (employee.getNumber() == employeeNumber) {
                    // Employee found, display details
                    JOptionPane.showMessageDialog(null, employee.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
                    return employee; // Return the found employee
                }
            }

            // If we reach here, the employee was not found
            JOptionPane.showMessageDialog(null, "Employee not found.", "Employee Search", JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (NumberFormatException e) {
            // Handle case where the input is not a valid integer
            JOptionPane.showMessageDialog(null, "Invalid number format. Please enter a valid employee number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////
    // Method Name : delete()                                        //
    // Return Type : void                                            //
    // Parameters : None                                             //
    // Purpose : Deletes the required Employee record from employees //
    ///////////////////////////////////////////////////////////////////
    public void delete() {
        // Prompt the user for the employee number to delete
        String input = JOptionPane.showInputDialog(null, "Enter Employee Number to Delete:");

        // If the user cancels or enters an invalid input
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input entered or canceled", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Parse the input to an integer (Employee number)
            int employeeNumber = Integer.parseInt(input);

            // Search for the employee in the employees list
            for (Employee employee : employees) {
                if (employee.getNumber() == employeeNumber) {
                    // Employee found, now remove it from the list
                    employees.remove(employee);

                    // Inform the user that the employee has been deleted
                    JOptionPane.showMessageDialog(null, "Employee " + employeeNumber + " deleted successfully.", "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                    return; // Exit the method after deletion
                }
            }

            // If we reach here, the employee was not found
            JOptionPane.showMessageDialog(null, "Employee not found.", "Employee Search", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            // Handle case where the input is not a valid number (employee number)
            JOptionPane.showMessageDialog(null, "Invalid employee number. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /////////////////////////////////////////////////////////////////
    // Method Name : edit()                                        //
    // Return Type : void                                          //
    // Parameters : None                                           //
    // Purpose : Edits the required Employee record in employees   //
    /////////////////////////////////////////////////////////////////
    
    
    public void edit() {
        // Prompt the user for the employee number
        String input = JOptionPane.showInputDialog(null, "Enter Employee Number to Edit:");

        // If the user cancels or enters an invalid input
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input entered or canceled", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Parse the input to an integer (Employee number)
            int employeeNumber = Integer.parseInt(input);

            // Search for the employee in the employees list
            for (Employee employee : employees) {
                if (employee.getNumber() == employeeNumber) {
                    // Employee found, now allow the user to edit their details

                    // Create input fields for all editable attributes
                    JComboBox<String> cbTitle = new JComboBox<>(new String[]{"Mr", "Ms", "Mrs", "Miss"});
                    cbTitle.setSelectedItem(employee.getTitle()); // Set current title
                    JTextField txtFirstName = new JTextField(employee.getFirstName());
                    JTextField txtSurname = new JTextField(employee.getSurname());
                    JTextField txtPhoneNumber = new JTextField(employee.getPhoneNumber());
                    JTextField txtSalary = new JTextField(String.valueOf(employee.getSalary()));

                    // Create the message for the dialog box
                    Object[] message = {
                        "Title:", cbTitle,
                        "First Name:", txtFirstName,
                        "Surname:", txtSurname,
                        "Phone Number:", txtPhoneNumber,
                        "Salary:", txtSalary
                    };

                    // Create a dialog to allow the user to edit the employee details
                    int option = JOptionPane.showConfirmDialog(null, message, "Edit Employee Details", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        // Update employee details based on user input
                        employee.setTitle((String) cbTitle.getSelectedItem());
                        employee.setFirstName(txtFirstName.getText());
                        employee.setSurname(txtSurname.getText());
                        employee.setPhoneNumber(txtPhoneNumber.getText());

                        // Validate salary input
                        try {
                            double salary = Double.parseDouble(txtSalary.getText());
                            employee.setSalary(salary);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid salary input. Please enter a valid number.", "Invalid Salary", JOptionPane.ERROR_MESSAGE);
                        }

                        // Inform the user that the employee details have been updated
                        JOptionPane.showMessageDialog(null, "Employee details updated successfully.", "Edit Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // User clicked cancel, do nothing
                        JOptionPane.showMessageDialog(null, "Edit operation canceled.", "Edit Canceled", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return; // Employee found and edited, exit the method
                }
            }

            // If we reach here, the employee was not found
            JOptionPane.showMessageDialog(null, "Employee not found.", "Employee Search", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            // Handle case where the input is not a valid number (employee number)
            JOptionPane.showMessageDialog(null, "Invalid employee number. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }


    /////////////////////////////////////////////////////////////////
    // Method Name : serializeEmployees()                          //
    // Return Type : void                                          //
    // Parameters : None                                           //
    // Purpose : Serializes the employees ArrayList to a file      //
    /////////////////////////////////////////////////////////////////
    public void serializeEmployees() {
        // Use try-with-resources to ensure streams are closed automatically
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(employeesFile))) {
            // Write the employees ArrayList to the employeesFile
            oos.writeObject(employees);

            // Notify the user of successful serialization
            JOptionPane.showMessageDialog(null, "Employees successfully saved to file.", 
                                          "Serialization", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // Handle exceptions and notify the user of an error during serialization
            JOptionPane.showMessageDialog(null, "Error saving employees: " + e.getMessage(), 
                                          "Serialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //////////////////////////////////////////////////////////////////
    // Method Name : deserializeEmployees()                        //
    // Return Type : void                                          //
    // Parameters : None                                           //
    // Purpose : Restores the employees ArrayList from a file      //
    //////////////////////////////////////////////////////////////////
    public void deserializeEmployees() {
        // Check if the file exists and is not empty before attempting to deserialize
        if (!employeesFile.exists() || employeesFile.length() == 0) {
            System.out.println("No data to deserialize.");
            
            // Initialize an empty employees list if no file exists
            employees = new ArrayList<>();
            return; // Exit the method as there is nothing to deserialize
        }

        // Use try-with-resources to ensure streams are closed automatically
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(employeesFile))) {
            // Read the employees ArrayList from the file
            employees = (ArrayList<Employee>) ois.readObject();
            
            // Check if the deserialized data is null (e.g., corrupted file)
            if (employees == null) {
                employees = new ArrayList<>(); // Initialize a new empty list if data is null
            } else if (!employees.isEmpty()) {
                // If the list is not empty, calculate the highest employee number
                int lastNumber = employees.stream()
                                          .mapToInt(Employee::getNumber) // Extract numbers from employees
                                          .max()                         // Find the maximum number
                                          .orElse(9999);                 // Default to 9999 if list is empty
                
                // Update the static `nextNumber` in the Employee class
                Employee.setNextNumber(lastNumber + 1);
            }
        } catch (FileNotFoundException e) {
            // File is missing; notify the user using a message dialog
            JOptionPane.showMessageDialog(null, "File not found: " + employeesFile.getName(), 
                                          "Deserialization Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            // Other issues like IO errors or incompatible class versions
            JOptionPane.showMessageDialog(null, "Error reading employees: " + e.getMessage(), 
                                          "Deserialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}