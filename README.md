# CSV File Manager
This Java program provides a flexible and user-friendly way to manage CSV files, allowing users to interact with the data through both a terminal-based interface and a graphical user interface (GUI). The program supports two types of CSV files: "Student.csv" and "Course.csv."

## Features
1. **Terminal Mode**
   - Create, read, update, and delete records in the CSV files.
   - Switch between managing "Student.csv" and "Course.csv" seamlessly.
   - Super delete option for clearing the entire CSV file.
2. **GUI Mode**
   - Edit data in a table-based UI.
   - Add, edit, and delete records visually.
   - Switch between "Student.csv" and "Course.csv" with ease.

## Usage
### **Terminal Mode**
1. Compile and run the program using a Java compiler.
```bash
javac Main.java
java Main
```
2. Follow the on-screen instructions to perform various operations.

### **GUI Mode**
1. Compile and run the GUI version of the program.
```bash
javac FileManagerGUI.java
java FileManagerGUI
```
2. Use the GUI to add, edit, and delete records. Press the "Switch" button to switch between managing "Student.csv" and "Course.csv."

#### Switching between Student.csv and Courses.csv
- The program allows you to switch between Student.csv and Courses.csv seamlessly. To switch to the Courses.csv file in the GUI, follow these steps:

  1. Press the "Switch" button in the GUI.
  2. The program will save the changes made in the GUI and switch to the Courses.csv file.
  
- To switch from the terminal to the GUI, compile and run the FileManagerGUI class separately.

## Dependencies
- The program is written in Java and does not have external dependencies beyond the standard Java libraries.

## Notes
- Make sure to have Java installed on your system to run the program.
