import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName1 = "Student.csv", fileName2 = "Course.csv";
        String[] studentArr = {"Name", "Id#", "Year Lvl", "Gender", "Course"};
        String[] courseArr = {"Name", "Id#"};
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */

        FileOutputStream fos1, fos2;
        PrintWriter students, courses;
        CsvFileManager student, course;

        if (!fileExists(fileName1)) {
            createNewFile(fileName1);
            fos1 = new FileOutputStream(fileName1);
            students = new PrintWriter(fos1);
            student = new CsvFileManager(students, fileName1, studentArr, "Name, Id#, Year Lvl, Gender, Course");
        } else {
            fos1 = new FileOutputStream(fileName1, true);
            students = new PrintWriter(fos1);
            student = new CsvFileManager(students, fileName1, studentArr);
        }

        if (!fileExists(fileName2)) {
            createNewFile(fileName2);
            fos2 = new FileOutputStream(fileName2);
            courses = new PrintWriter(fos2);
            course = new CsvFileManager(courses, fileName2, courseArr, "Course Name, Course Id#");
        } else {
            fos2 = new FileOutputStream(fileName2, true);
            courses = new PrintWriter(fos2);
            course = new CsvFileManager(courses, fileName2, courseArr);
        }

        /* Put the program here */
        /* Put the program here */
        /* Put the program here */
        boolean keepRunning = true;
        boolean switchFile = false;
        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        while (keepRunning) {
            System.out.println("\n- - - - - - - - - - -");
            if (!switchFile) student.list(); else course.list();

            System.out.println("- - - - - - - - - - -\n");
            System.out.println("1. Create");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. Read");
            if (!switchFile) System.out.println("5. Switch to Courses.csv"); else System.out.println("5. Switch to Students.csv");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }

            if (!switchFile) {
                switch (choice) {
                    case 1:
                        System.out.println("\nEnter student details:");
                        System.out.print("Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("ID: ");
                        String newId = scanner.nextLine();
                        System.out.print("Year Level: ");
                        String newYearLevel = scanner.nextLine();
                        System.out.print("Gender: ");
                        String newGender = scanner.nextLine();
                        System.out.print("Course: ");
                        String newCourse = scanner.nextLine();

                        student.create(newName, newId, newYearLevel, newGender, newCourse);
                        break;
                    case 2:
                        // Update logic
                        System.out.print("\nEnter the index of the record you want to update: ");
                        int indexToUpdate = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter updated student details:");
                        System.out.print("Name: ");
                        String updatedName = scanner.nextLine();
                        System.out.print("ID: ");
                        String updatedId = scanner.nextLine();
                        System.out.print("Year Level: ");
                        String updatedYearLevel = scanner.nextLine();
                        System.out.print("Gender: ");
                        String updatedGender = scanner.nextLine();
                        System.out.print("Course: ");
                        String updatedCourse = scanner.nextLine();

                        // Store the updated data in an array
                        String[] updatedLine = {updatedName, updatedId, updatedYearLevel, updatedGender, updatedCourse};
                        student.update(indexToUpdate, updatedLine);
                        break;
                    case 3:
                        // Delete logic
                        System.out.println("\n1. Regular Delete");
                        System.out.println("2. Super Delete (clearCsvFile)");
                        System.out.print("Choose delete option: ");

                        int deleteOption = Integer.parseInt(scanner.nextLine());

                        switch (deleteOption) {
                            case 1:
                                System.out.println("\nEnter the index of the record you want to delete: ");
                                int indexToDelete = Integer.parseInt(scanner.nextLine());
                                student.delete(indexToDelete);
                                break;
                            case 2:
                                System.out.print("\nSuper Delete initiated. Are you sure? (Y/N):");
                                String confirmation = scanner.nextLine().trim().toLowerCase();
                                if (confirmation.equals("y")) {
                                    student.clearCsvFile();
                                    System.out.println("\nAll records deleted. The CSV file is now empty.");
                                } else {
                                    System.out.println("\nSuper Delete aborted.");
                                }
                                break;
                            default:
                                System.out.println("\nInvalid delete option. No deletion performed.");
                        }
                        break;
                    case 4:
                        // Read logic
                        System.out.println("\nEnter the index of the record you want to read: ");
                        int indexToRead = Integer.parseInt(scanner.nextLine());

                        student.read(indexToRead);
                        break;
                    case 5:
                        switchFile = true;
                        break;
                    case 6:
                        System.out.println("\n1. Cancel");
                        System.out.println("2. Exit (SURE!)");
                        System.out.print("Select an option: ");

                        int exitProgram = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (exitProgram) {
                            case 1:
                                System.out.println("Operation canceled. Returning to the main menu.");
                                break;
                            case 2:
                                keepRunning = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please choose again.");
                }
            } else {
                switch (choice) {
                    case 1:
                        System.out.println("\nEnter course details:");
                        System.out.print("Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("ID: ");
                        String newId = scanner.nextLine();

                        course.create(newName, newId);
                        break;
                    case 2:
                        // Update logic
                        System.out.println("\nEnter the index of the record you want to update: ");
                        int indexToUpdate = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter updated course details:");
                        System.out.print("Name: ");
                        String updatedName = scanner.nextLine();
                        System.out.print("ID: ");
                        String updatedId = scanner.nextLine();

                        // Store the updated data in an array
                        String[] updatedLine = {updatedName, updatedId};
                        course.update(indexToUpdate, updatedLine);
                        break;
                    case 3:
                        // Delete logic
                        System.out.println("\n1. Regular Delete");
                        System.out.println("2. Super Delete (clearCsvFile)");
                        System.out.print("Choose delete option: ");

                        int deleteOption = Integer.parseInt(scanner.nextLine());

                        switch (deleteOption) {
                            case 1:
                                System.out.println("\nEnter the index of the record you want to delete: ");
                                int indexToDelete = Integer.parseInt(scanner.nextLine());
                                course.delete(indexToDelete);
                                break;
                            case 2:
                                System.out.print("\nSuper Delete initiated. Are you sure? (Y/N):");
                                String confirmation = scanner.nextLine().trim().toLowerCase();
                                if (confirmation.equals("y")) {
                                    course.clearCsvFile();
                                    System.out.println("\nAll records deleted. The CSV file is now empty.");
                                } else {
                                    System.out.println("\nSuper Delete aborted.");
                                }
                                break;
                            default:
                                System.out.println("\nInvalid delete option. No deletion performed.");
                        }
                        break;
                    case 4:
                        // Read logic
                        System.out.println("\nEnter the index of the record you want to read: ");
                        int indexToRead = Integer.parseInt(scanner.nextLine());

                        course.read(indexToRead);
                        break;
                    case 5:
                        switchFile = false;
                        break;
                    case 6:
                        System.out.println("\n1. Cancel");
                        System.out.println("2. Exit (SURE!)");
                        System.out.print("Select an option: ");

                        int exitProgram = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (exitProgram) {
                            case 1:
                                System.out.println("Operation canceled. Returning to the main menu.");
                                break;
                            case 2:
                                keepRunning = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please choose again.");
                }
            }
        }
        /* Put the program here */
        /* Put the program here */
        /* Put the program here */
        students.close();
        courses.close();
        System.out.println("\nFile /Student.csv/ has been written...");
        System.out.println("File /Course.csv/ has been written...");
    }

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory() && file.length() > 0;
    }
    private static void createNewFile(String fileName) {
        // Create a new file and write the header
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName))) {
            String header = "Name, Id#, Year Lvl, Gender, Course";
            writer.println(header);
            writer.flush();
        } catch (FileNotFoundException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
    }
}