import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    protected static String fileName1 = "Student.csv", fileName2 = "Course.csv";
    private static final String[] studentArr = {"Name", "Id#", "Year Lvl", "Gender", "Course"};
    private static final String[] courseArr = {"Name", "Id#"};
    private static final String studentHeader = "Name, Id#, Year Lvl, Gender, Course";
    private static final String courseHeader = "Course Name, Course Id#";
    private static PrintWriter students, courses;
    private static CsvFileManager student, course;
    private static Set<String> courseIds;
    private static final boolean activateGUI = false;
    private static boolean switchFile = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        courseIds = readCourseIds(fileName2);

        /*
          Checks if the specified file exists. If it does not exist, creates a new file with the specified header.
          Initializes file output streams and print writers for the file. If the file exists, appends to the existing file.

          @param fileName1     The name of the first file to check/create.
         * @param fileName2     The name of the second file to check/create.
         * @param studentHeader The header string for the student file.
         * @param courseHeader  The header string for the course file.
         * @param courseIds     A set of course IDs extracted from the existing course file.
         * @param activateGUI   A flag indicating whether the GUI is activated.
         * @throws FileNotFoundException If an error occurs while creating the file output stream.
         */
        FileOutputStream fos1;
        if (fileExists(fileName1)) {
            createNewFile(fileName1, studentHeader);
            fos1 = new FileOutputStream(fileName1);
            students = new PrintWriter(fos1);
            if (!activateGUI) student = new CsvFileManager(students, fileName1, studentHeader, courseIds);
        } else {
            fos1 = new FileOutputStream(fileName1, true);
            students = new PrintWriter(fos1);
            if (!activateGUI) student = new CsvFileManager(students, fileName1, courseIds);
        }
        FileOutputStream fos2;
        if (fileExists(fileName2)) {
            createNewFile(fileName2, courseHeader);
            fos2 = new FileOutputStream(fileName2);
            courses = new PrintWriter(fos2);
            if (!activateGUI) course = new CsvFileManager(courses, fileName2, courseHeader, courseIds);
        } else {
            fos2 = new FileOutputStream(fileName2, true);
            courses = new PrintWriter(fos2);
        }

        /*
          Handles the program's logic for both GUI and terminal modes.
          If GUI mode is activated, initializes a new FileManagerGUI instance.
          If terminal mode is activated, provides a text-based menu for user interaction.
          <p>
          case 1: create
          case 2: update
          case 3: delete
          case 4: read
          case 5: switchFile
          case 6: exit application
          <p>
          @param activateGUI     A flag indicating whether the GUI mode is activated.
         * @param scanner          A Scanner object for user input in terminal mode.
         * @param fileName1        The name of the first CSV file.
         * @param fileName2        The name of the second CSV file.
         * @param student          A CsvFileManager instance for managing student data.
         * @param course           A CsvFileManager instance for managing course data.
         * @param switchFile       A flag indicating whether the user has switched between student and course data.
         * @param courseIds        A set of course IDs extracted from the existing course file.
         * @throws FileNotFoundException If an error occurs while creating file output streams.
         */
        if (activateGUI) {
            boolean firstTime;
            System.out.println("Is this your first time? (y/n)");
            String response = scanner.nextLine().toLowerCase();
            if  (response.equals("n"))  firstTime = false; else firstTime = true;
            new FileManagerGUI(students, fileName1, studentArr, studentHeader, switchListener, courseIds, firstTime);
        }
        if (!activateGUI) {
            boolean keepRunning = true;
            int choice = 0;

            while (keepRunning) {
                System.out.println("\nCourse #ID values:");
                int i = 0;
                for (String courseId : courseIds) {
                    System.out.print(courseId + "\t");
                    i++;
                    if (i > 2) {
                        System.out.println();
                        i = 0;
                    }
                }
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

                switch (choice) {
                    case 1:
                        if (!switchFile) {
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

                            if (newCourse.isEmpty()) {newCourse = "Not Enrolled";}
                            student.create(newName, newId, newYearLevel, newGender, newCourse);

                        } else {
                            System.out.println("\nEnter course details:");
                            System.out.print("Name: ");
                            String newName = scanner.nextLine();
                            System.out.print("ID: ");
                            String newId = scanner.nextLine();

                            course.create(newName, newId);
                        }
                        break;
                    case 2:
                        if (!switchFile) {
                            // Update logic
                            System.out.print("\nEnter the index of the record you want to update: ");
                            int indexToUpdate = Integer.parseInt(scanner.nextLine());

                            // Retrieve the existing data for the specified index
                            String[] existingData = student.getLine(indexToUpdate);

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

                            if (updatedName.isEmpty()) updatedName = existingData[0];
                            if (updatedId.isEmpty()) updatedId = existingData[1];
                            if (updatedYearLevel.isEmpty()) updatedYearLevel = existingData[2];
                            if (updatedGender.isEmpty()) updatedGender = existingData[3];
                            if (updatedCourse.isEmpty()) updatedCourse = existingData[4];

                            // Store the updated data in an array
                            String[] updatedLine = {updatedName, updatedId, updatedYearLevel, updatedGender, updatedCourse};
                            student.update(indexToUpdate, updatedLine);

                        } else {
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
                        }
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
                                if (!switchFile) student.delete(indexToDelete); else course.delete(indexToDelete);
                                break;
                            case 2:
                                // Delete from-to logic
                                System.out.println("\nEnter the range of records you want to delete (from-to): ");
                                System.out.print("From index: ");
                                int fromIndex = 0, toIndex = 0;
                                try {
                                    fromIndex = Integer.parseInt(scanner.nextLine());
                                    System.out.print("To index: ");
                                    toIndex = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {System.out.println("\u001B[31mOption #number not found:\n\t" + e + "\u001B[0m");}


                                if (!switchFile) {
                                    if (fromIndex < 1 || toIndex >= student.getLinesList().size()) {
                                        System.out.println("Invalid range. Please enter a valid range.");
                                    } else {
                                        student.delete(fromIndex, toIndex);
                                        System.out.println("Records from index " + fromIndex + " to " + toIndex + " deleted.");
                                    }
                                } else {
                                    if (fromIndex < 1 || toIndex >= course.getLinesList().size()) {
                                        System.out.println("Invalid range. Please enter a valid range.");
                                    } else {
                                        course.delete(fromIndex, toIndex);
                                        System.out.println("Records from index " + fromIndex + " to " + toIndex + " deleted.");
                                    }
                                }
                                break;
                            case 3:
                                System.out.print("\nSuper Delete initiated. Are you sure? (Y/N):");
                                String confirmation = scanner.nextLine().trim().toLowerCase();
                                if (!switchFile) {
                                    if (confirmation.equals("y")) {
                                        student.clearCsvFile();
                                        System.out.println("\nAll records deleted. The CSV file is now empty.");
                                    } else {
                                        System.out.println("\nSuper Delete aborted.");
                                    }
                                } else {
                                    if (confirmation.equals("y")) {
                                        course.clearCsvFile();
                                        System.out.println("\nAll records deleted. The CSV file is now empty.");
                                    } else {
                                        System.out.println("\nSuper Delete aborted.");
                                    }
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
                        courseIds = readCourseIds(fileName2);
                        if (!switchFile) {
                            switchFile = true;
                            course = new CsvFileManager(courses, fileName2, courseIds);
                        } else {
                            switchFile = false;
                            student = new CsvFileManager(students, fileName1, courseIds);
                        }
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
            System.out.println("\nFile /Course.csv/ has been written...");
            System.out.println("File /Student.csv/ has been written...");
            students.close();
            courses.close();
        }
    }

    /**
     * A listener implementation for handling file manager switches in the FileManagerGUI.
     * This listener toggles the switchFile flag and updates the GUI accordingly.
     * <p>
     * Called when a file manager switch event occurs.
     * Toggles the switchFile flag and updates the FileManagerGUI accordingly.
     */
    final static FileManagerSwitchedListener switchListener = new FileManagerSwitchedListener() {
        @Override
        public void onFileManagerSwitched() {
            switchFile = !switchFile;
            System.out.println("FileManagerGUI is switched!");
            if (!switchFile) {
                courseIds = readCourseIds(fileName2);
                new FileManagerGUI(students, fileName1, studentArr, studentHeader, switchListener, courseIds, false);
            }
            if (switchFile) {
                new FileManagerGUI(courses, fileName2, courseArr, courseHeader, switchListener, courseIds, false);
            }
        }
    };

    /**
     * Checks if a file exists and is not a directory and has a non-zero length.
     *
     * @param fileName The name of the file to check.
     * @return {@code true} if the file exists, is not a directory, and has a non-zero length; {@code false} otherwise.
     */
    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return !file.exists() || file.isDirectory() || file.length() <= 0;
    }

    /**
     * Creates a new file with the specified name and writes the header to it.
     * If the file already exists, it will be overwritten.
     *
     * @param fileName The name of the file to create.
     * @param header   The header string to write to the file.
     */
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

    /**
     * Reads course IDs from the specified CSV file.
     * Assumes that the course ID is located in the second column of each row.
     *
     * @param fileName The name of the CSV file to read course IDs from.
     * @return A set containing unique course IDs extracted from the file.
     */
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
        } catch (IOException e) {System.out.println("\u001B[31mAn error occurred while reading Course.csv:\n\t" + e + "\u001B[0m");}
        return courseIds;
    }
}