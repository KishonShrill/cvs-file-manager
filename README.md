# CSV File Manager
This Java program provides a flexible and user-friendly way to manage CSV files, allowing users to interact with the data through both a terminal-based interface and a graphical user interface (GUI). The program supports two types of CSV files: "Student.csv" and "Course.csv."

## ✨ Updated Features - v.2.09  ✨
- **Sort Functionality**: Now you can easily arrange your data using the new sorting feature. Whether you want to organize by name, ID#, year level, gender, or course, it's just a click away!
- **Smart Data Overwrite**: Worried about accidentally duplicating data? Our CSV Editor now prompts you if the ID# you're trying to add already exists. You'll have the option to overwrite the existing data or cancel the operation.
- **Course Filtering**: Say goodbye to clutter! The course dropdown menu now displays only the available courses from the Course.csv file. No more guessing or manual entries - it's streamlined and hassle-free.

## Table of contents

- [Features](#features)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Notes](#notes)
- [Author](#author)

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
### **Terminal & GUI Mode**
1. Compile and run the program using IntelliJ Java Compiler.
2. To switch from Terminal to GUI Mode, edit the following code from false to true:
```java
private static final boolean activateGUI = false;
```
> The code will be found on the starting initialization of Main.java
3. Use the GUI to add, edit, and delete records. Press the "Switch" button to switch between managing "Student.csv" and "Course.csv."

#### Switching between Student.csv and Courses.csv
- The program allows you to switch between Student.csv and Courses.csv seamlessly. To switch to the Courses.csv file in the GUI, follow these steps:

  1. Press the "Switch" button in the GUI.
  2. The program will save the changes made in the GUI and switch to the Courses.csv file.
  
- To switch from the terminal to the GUI, compile and run the FileManagerGUI class separately.

## Dependencies
- The program is written in Java and does not have external dependencies beyond the standard Java libraries.

## Notes
- Make sure to have Java installed on your system to run the program.
- Make sure the file names are **EXACTLY** `Course.csv` & `Student.csv`

## Author
- Website Portfolio - [ChriscentProduction](https://kishonshrill.github.io/website-portfolio/)
- Email - [chriscentlouisjune.pingol@g.msuiit.edu.ph](mailto:chriscentlouisjune.pingol@g.msuiit.edu.ph)
