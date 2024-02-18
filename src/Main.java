import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static String fileName1 = "Student.csv", fileName2 = "Course.csv";
    public static String[] studentArr = {"Name", "Id#", "Year Lvl", "Gender", "Course"};
    public static String[] courseArr = {"Name", "Id#"};
    public static String studentHeader = "Name, Id#, Year Lvl, Gender, Course";
    public static String courseHeader = "Course Name, Course Id#";
    public static FileOutputStream fos1, fos2;
    public static PrintWriter students, courses;
    public static CsvFileManager student, course;
    public static Set<String> courseIds;
    public static boolean activateGUI = false;
    public static boolean activateTerminal = true;
    public static boolean switchFile = false;

    public static void main(String[] args) throws FileNotFoundException {
        // Checker
        courseIds = readCourseIds(fileName2);
        System.out.println("Course #ID values:");
        for (String courseId : courseIds) {System.out.println(courseId);}
        // Checker

        if (!fileExists(fileName1)) {
            createNewFile(fileName1, studentHeader);
            fos1 = new FileOutputStream(fileName1);
            students = new PrintWriter(fos1);
            if (!activateGUI) student = new CsvFileManager(students, fileName1, studentHeader, courseIds);
        } else {
            fos1 = new FileOutputStream(fileName1, true);
            students = new PrintWriter(fos1);
            if (!activateGUI) student = new CsvFileManager(students, fileName1, courseIds);
        }

        if (!fileExists(fileName2)) {
            createNewFile(fileName2, courseHeader);
            fos2 = new FileOutputStream(fileName2);
            courses = new PrintWriter(fos2);
            if (!activateGUI) course = new CsvFileManager(courses, fileName2, courseHeader, courseIds);
        } else {
            fos2 = new FileOutputStream(fileName2, true);
            courses = new PrintWriter(fos2);
        }


        /* External Program */
        /* External Program */
        if (activateGUI) {
            new FileManagerGUI(students, fileName1, studentArr, studentHeader, switchListener, courseIds);
        }
        /* External Program */
        /* External Program */

        /* Internal Program */
        /* Internal Program */
        if (!activateGUI && activateTerminal) {
            boolean keepRunning = true;
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

                            // Check if newCourse is empty
                            if (newCourse.isEmpty()) {
                                newCourse = "Not Enrolled";
                            }

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
                            System.out.println("2. Delete from-to");
                            System.out.println("3. Super Delete (clearCsvFile)");
                            System.out.print("Choose delete option: ");

                            int deleteOption = Integer.parseInt(scanner.nextLine());

                            switch (deleteOption) {
                                case 1:
                                    System.out.println("\nEnter the index of the record you want to delete: ");
                                    int indexToDelete = Integer.parseInt(scanner.nextLine());
                                    student.delete(indexToDelete);
                                    break;
                                case 2:
                                    // Delete from-to logic
                                    System.out.println("\nEnter the range of records you want to delete (from-to): ");
                                    System.out.print("From index: ");
                                    int fromIndex = Integer.parseInt(scanner.nextLine());
                                    System.out.print("To index: ");
                                    int toIndex = Integer.parseInt(scanner.nextLine());

                                    if (fromIndex < 1 || toIndex >= student.getLinesList().size()) {
                                        System.out.println("Invalid range. Please enter a valid range.");
                                    } else {
                                        student.delete(fromIndex, toIndex);
                                        System.out.println("Records from index " + fromIndex + " to " + toIndex + " deleted.");
                                    }
                                    break;
                                case 3:
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
                            courseIds = readCourseIds(fileName2);
                            System.out.println("\nCourse #ID values:");
                            for (String courseId : courseIds) {
                                System.out.println(courseId);
                            }
                            course = new CsvFileManager(courses, fileName2, courseIds);
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
                            System.out.println("2. Delete from-to");
                            System.out.println("3. Super Delete (clearCsvFile)");
                            System.out.print("Choose delete option: ");

                            int deleteOption = Integer.parseInt(scanner.nextLine());

                            switch (deleteOption) {
                                case 1:
                                    System.out.println("\nEnter the index of the record you want to delete: ");
                                    int indexToDelete = Integer.parseInt(scanner.nextLine());
                                    course.delete(indexToDelete);
                                    break;
                                case 2:
                                    // Delete from-to logic
                                    System.out.println("\nEnter the range of records you want to delete (from-to): ");
                                    System.out.print("From index: ");
                                    int fromIndex = Integer.parseInt(scanner.nextLine());
                                    System.out.print("To index: ");
                                    int toIndex = Integer.parseInt(scanner.nextLine());

                                    if (fromIndex < 1 || toIndex >= course.getLinesList().size()) {
                                        System.out.println("Invalid range. Please enter a valid range.");
                                    } else {
                                        course.delete(fromIndex, toIndex);
                                        System.out.println("Records from index " + fromIndex + " to " + toIndex + " deleted.");
                                    }
                                    break;
                                case 3:
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
                            courseIds = readCourseIds(fileName2);
                            System.out.println("\nCourse #ID values:");
                            for (String courseId : courseIds) {
                                System.out.println(courseId);
                            }
                            student = new CsvFileManager(students, fileName1, courseIds);
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
            students.close();
            courses.close();
            System.out.println("\nFile /Course.csv/ has been written...");
            System.out.println("File /Student.csv/ has been written...");
        }
        /* Internal Program */
        /* Internal Program */
    }

    final static FileManagerSwitchedListener switchListener = new FileManagerSwitchedListener() {
        @Override
        public void onFileManagerSwitched() {
            switchFile = !switchFile;
            System.out.println("FileManagerGUI is switched!");
            if (!switchFile) {
                new FileManagerGUI(students, fileName1, studentArr, studentHeader, switchListener, courseIds);
            }
            if (switchFile) {
                new FileManagerGUI(courses, fileName2, courseArr, courseHeader, switchListener, courseIds);
            }
        }
    };

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory() && file.length() > 0;
    }
    private static void createNewFile(String fileName, String header) {
        // Create a new file and write the header
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName))) {
            writer.println(header);
            writer.flush();
        } catch (FileNotFoundException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
    }

    // Step 2: Read Course #ID values from Student.csv
    private static Set<String> readCourseIds(String fileName) {
        Set<String> courseIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip the header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 2) {
                    courseIds.add(columns[1].trim());  // Assuming Course #ID is in the second column
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Student.csv:" + e);
        }

        return courseIds;
    }
}